package com.test.demo.Controller.APIController.banking;

import com.test.demo.DTO.Banking.WithdrawalDTO;
import com.test.demo.DTO.MoneyTransfer.TransferMoneyRequestDTO;
import com.test.demo.DTO.MoneyTransfer.TransferMoneyResponseDTO;
import com.test.demo.DTO.Transactions.TransactionDetailsDTO;
import com.test.demo.DTO.Transactions.TransactionSummary;
import com.test.demo.DTO.Transactions.TransactionsDTO;
import com.test.demo.Repository.UserRepository;
import com.test.demo.Service.Banking.BankingService;
import com.test.demo.Service.Banking.PdfService;
import com.test.demo.config.CustomError.UserNotFoundException;
import com.test.demo.models.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/banking")
public class BankingController {


    @Autowired
    private BankingService bankingService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PdfService pdfService;


    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDetailsDTO> withdrawalRequest(@RequestBody WithdrawalDTO withdrawalDTO) {
        System.out.println("Banking");
        return bankingService.withdrawalService(withdrawalDTO);
    }

    @PostMapping("/credit")
    public ResponseEntity<TransactionDetailsDTO> creditRequest(@RequestBody WithdrawalDTO withdrawalDTO){
        return bankingService.creditService(withdrawalDTO);
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<Page<TransactionsDTO>> historyRequest(@PathVariable("id") Long id
                                                                , @RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "size", defaultValue = "10") int size
                                                                ){
        return bankingService.historyService(id, page, size, "dateDesc");
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferMoneyResponseDTO> transferMoneyRequest(@RequestBody TransferMoneyRequestDTO transferMoneyRequestDTO){
        return bankingService.transferMoneyService(transferMoneyRequestDTO);
    }

    @GetMapping("/download-statement/{id}")
    public void downloadStatementAPI(
            HttpServletResponse response,
            @PathVariable Long id,
            @RequestParam(defaultValue = "50") int limit
    ) throws IOException {
        User user = userRepository.findUserByIdAndIsDeleted(id, 0).orElseThrow(() -> new UserNotFoundException("User not found."));
        List<TransactionsDTO> transactions = bankingService.historyService(user.getId(), 0, limit, "dateDesc").getBody().getContent();
        pdfService.export(response, transactions, user.getUsername());
    }
}
