package com.test.demo.Controller.ViewController.banking;

import com.test.demo.DTO.Transactions.TransactionSummary;
import com.test.demo.DTO.Transactions.TransactionsDTO;
import com.test.demo.DTO.MoneyTransfer.TransferMoneyRequestDTO;
import com.test.demo.Repository.UserRepository;
import com.test.demo.Service.Banking.BankingService;
import com.test.demo.Service.Banking.PdfService;
import com.test.demo.config.CustomError.UserNotFoundException;
import com.test.demo.models.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class AccountController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankingService bankingService;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/banking/transfer")
    public String transferPage(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsernameAndIsDeleted(username, 0).orElseThrow(null);
        model.addAttribute("user", user);
        return "TransferPages/transfer";
    }


    @PostMapping("/perform_transfer")
    public String performTransfer(
            @RequestParam("receiverId") Long receiverId,
            @RequestParam("amount") BigDecimal amount,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {
        try {
            String senderUsername = principal.getName();
            User sender = userRepository.findByUsernameAndIsDeleted(senderUsername, 0).orElseThrow(() -> new UserNotFoundException("Your account was not found."));

            User receiver = userRepository.findUserByIdAndIsDeleted(receiverId, 0).orElseThrow(() -> new UserNotFoundException("Account Number : " + receiverId + " not found."));
            TransferMoneyRequestDTO requestDTO = new TransferMoneyRequestDTO();
            requestDTO.setToId(receiver.getId());
            requestDTO.setFromId(sender.getId());
            requestDTO.setAmount(amount);

            bankingService.transferMoneyService(requestDTO);

            redirectAttributes.addFlashAttribute("amount", amount);
            redirectAttributes.addFlashAttribute("receiver", receiver.getUsername());

            return "redirect:/success";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/banking/transfer";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/banking/transfer";
        }
    }

    @GetMapping("/success")
    public ModelAndView showSuccessPage(Model model) {
        // Security: If no amount is present (user typed URL manually), kick them back
        ModelAndView mv = new ModelAndView();
        if (!model.containsAttribute("amount")) {
//            return "redirect:/dashboard";
            mv.setViewName("/dashboard");

        }else{
            mv.setViewName("TransferPages/success");
        }
        return mv;

//        return "TransferPages/success"; // NOW we return the file path
    }

    @GetMapping("/history")
    public String historyPage(
            Model model,
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false, name = "to") String tDate,
            @RequestParam(required = false, name = "from") String fDate,
            @RequestParam(required = false, name="sort", defaultValue = "dateDesc") String sort
    ) {

        String username = principal.getName();
        User user = userRepository.findByUsernameAndIsDeleted(username, 0).orElseThrow(() -> new UserNotFoundException("User not found."));
        ResponseEntity<Page<TransactionsDTO>> response;
//        System.out.println(sDate);
        if ((tDate == null && fDate == null)  || (fDate.isEmpty())) {
            response = bankingService.historyService(user.getId(), page, 5, sort);
        }else{
            LocalDateTime fromDate = LocalDateTime.parse(fDate+"T00:00:00");
            LocalDateTime toDate = LocalDateTime.parse(tDate+"T11:59:59");
            response = bankingService.historyService(user.getId(), page, 5, toDate, fromDate, sort);
        }
        Page<TransactionsDTO> transactionsPage = response.getBody();
        if (!transactionsPage.isEmpty()) {
            model.addAttribute("transactions", transactionsPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", transactionsPage.getTotalPages());
            model.addAttribute("totalItems", transactionsPage.getTotalElements());
        }

        return "TransferPages/history";
    }

    @GetMapping("/export-transactions")
    public void exportToPDF(HttpServletResponse response,
                            Principal principal,
                            @RequestParam(required = false, name = "from") String fDate,
                            @RequestParam(required = false, name = "to") String tDate,
                            @RequestParam(required = false, name = "sort", defaultValue = "dateDesc") String sort
                            ) throws IOException {
        String username = principal.getName();
        User user = userRepository.findByUsernameAndIsDeleted(username, 0).orElseThrow(() -> new UserNotFoundException("User not found."));
        List<TransactionsDTO> transactions;
        if((fDate == null && tDate == null) || (fDate.isEmpty())) {
            transactions = bankingService.historyService(user.getId(), 0, 100, sort).getBody().getContent();
        }else{
            LocalDateTime fromDate = LocalDateTime.parse(fDate+"T00:00:00");
            LocalDateTime toDate = LocalDateTime.parse(tDate+"T11:59:59");
            transactions = bankingService.historyService(user.getId(), 0, 100 , toDate, fromDate, sort).getBody().getContent();
        }
        System.out.println(transactions);
        pdfService.export(response, transactions, username);
    }
}
