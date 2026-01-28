package com.test.demo.Service.Banking;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.test.demo.DTO.Transactions.TransactionSummary;
import com.test.demo.DTO.Transactions.TransactionsDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PdfService {
    public void export(HttpServletResponse response, List<TransactionsDTO> transactions, String username) throws IOException{
        response.setContentType("application/pdf");
        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transactions_" + username +"_"+currentDateTime+".pdf";
        response.setHeader(headerKey, headerValue);

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE);
        Paragraph title = new Paragraph("Spring Bank Statement.", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        Font subFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.DARK_GRAY);
        Paragraph subTitle = new Paragraph("Statement for: " + username + "\nDate: " + currentDateTime, subFont);
        subTitle.setAlignment(Paragraph.ALIGN_CENTER);
        subTitle.setSpacingAfter(20);
        document.add(subTitle);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{2.0f, 3.5f, 2.5f, 2.0f, 1.5f, 2.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table, transactions);

        document.add(table);
        document.close();
    }

    private void writeTableData(PdfPTable table, List<TransactionsDTO> transactions) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.black);

        for(TransactionsDTO txn: transactions){
            table.addCell(new Phrase(String.valueOf(txn.getTransactionType()), font));
            table.addCell(new Phrase(txn.getMessage(), font));
            table.addCell(new Phrase(String.valueOf(txn.getDate()), font));
            table.addCell(new Phrase("₹ " + txn.getAmount(), font));

            PdfPCell statusCell = new PdfPCell(new Phrase(String.valueOf(txn.getPaymentStatus()), font));
            if("SUCCESS".equals(String.valueOf(txn.getPaymentStatus()))){
                statusCell.setBackgroundColor(new Color(212, 237, 218));
            }else{
                statusCell.setBackgroundColor(new Color(248, 215, 218));
            }
            table.addCell(statusCell);
            table.addCell(new Phrase("₹ " + txn.getRemainingBalance(), font));
        }
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(8);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.WHITE);
        String[] headers = {"Type", "Description", "Date", "Amount", "Status", "Balance"};
        for(String header : headers){
            cell.setPhrase(new Phrase(header, font));
            table.addCell(cell);
        }
    }
}
