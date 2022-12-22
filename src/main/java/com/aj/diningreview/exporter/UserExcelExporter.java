package com.aj.diningreview.exporter;

import com.aj.diningreview.model.User;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserExcelExporter extends AbstractExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public UserExcelExporter() {
        workbook = new XSSFWorkbook();
    }
    public void export(List<User> userList, HttpServletResponse httpServletResponse) throws IOException {
        super.setResponseHeader(httpServletResponse, "application/octet-stream", ".xlsx");

        writeHeaderLine();
        writeDataLines(userList);

        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }

    private void writeDataLines(List<User> userList) {
        int rowIndex = 1;

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(false);
        font.setFontHeight(14);
        cellStyle.setFont(font);

        for (User user : userList) {
            XSSFRow row = sheet.createRow(rowIndex++);
            int columnIndex = 0;

            createCell(row, columnIndex++, user.getId(), cellStyle);
            createCell(row, columnIndex++, user.getName(), cellStyle);
            createCell(row, columnIndex++, user.getEmail(), cellStyle);
            createCell(row, columnIndex++, user.getCity(), cellStyle);
            createCell(row, columnIndex++, user.getState(), cellStyle);
            createCell(row, columnIndex++, user.getZipCode(), cellStyle);
            createCell(row, columnIndex++, user.getHasEggAllergy(), cellStyle);
            createCell(row, columnIndex++, user.getHasPeanutAllergy(), cellStyle);
            createCell(row, columnIndex++, user.getHasDairyAllergy(), cellStyle);
            createCell(row, columnIndex++, user.getEnabled(), cellStyle);
        }
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
        XSSFRow row = sheet.createRow(0);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        createCell(row, 0, "User ID", cellStyle);
        createCell(row, 1, "Username", cellStyle);
        createCell(row, 2, "E-mail", cellStyle);
        createCell(row, 3, "City", cellStyle);
        createCell(row, 4, "State", cellStyle);
        createCell(row, 5, "Zip", cellStyle);
        createCell(row, 6, "Egg allergy", cellStyle);
        createCell(row, 7, "Peanut allergy", cellStyle);
        createCell(row, 8, "Dairy allergy", cellStyle);
        createCell(row, 9, "Enabled", cellStyle);
    }

    private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle style) {
        XSSFCell cell = row.createCell(columnIndex);
        sheet.autoSizeColumn(columnIndex);

        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);
    }
}