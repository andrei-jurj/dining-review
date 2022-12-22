package com.aj.diningreview.exporter;

import com.aj.diningreview.model.User;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UserPdfExporter extends AbstractExporter {

    public void export(List<User> userList, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/pdf", ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph paragraph = new Paragraph("List of users", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);
        table.setWidths(new float[]  {1.5f, 3.5f, 5.5f, 3.5f, 3.5f, 3.1f, 2.2f, 2.2f, 2.2f, 2.5f});

        writeTableHeader(table);
        writeTableData(table, userList);

        document.add(paragraph);

        document.add(table);

        document.close();
    }

    private void writeTableData(PdfPTable table, List<User> userList) {
        for (User user : userList) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getName());
            table.addCell(user.getEmail());
            table.addCell(user.getCity());
            table.addCell(user.getState());
            table.addCell(user.getZipCode());
            table.addCell(String.valueOf(user.getHasEggAllergy()));
            table.addCell(String.valueOf(user.getHasPeanutAllergy()));
            table.addCell(String.valueOf(user.getHasDairyAllergy()));
            table.addCell(String.valueOf(user.getEnabled()));
        }
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(8);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Username", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("City", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("State", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Zip", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Egg allergy", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Peanut allergy", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Dairy allergy", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Enabled", font));
        table.addCell(cell);
    }
}