package com.aj.diningreview.exporter;

import com.aj.diningreview.model.User;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserCsvExporter {
    public void export(List<User> userList, HttpServletResponse httpServletResponse) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormat.format(new Date());
        String fileName = "users_" + timestamp + ".csv";

        httpServletResponse.setContentType("text/csv");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        httpServletResponse.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(httpServletResponse.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"User ID", "Username", "E-mail", "City", "State", "Zip", "Egg allergy", "Peanut allergy", "Dairy Allergy", "Enabled"};
        String[] fieldMapping = {"id", "name", "email", "city", "state", "zipCode", "hasEggAllergy", "hasPeanutAllergy", "hasDairyAllergy", "enabled"};

        csvBeanWriter.writeHeader(csvHeader);

        for (User user : userList) {
            csvBeanWriter.write(user, fieldMapping);
        }

        csvBeanWriter.close();
    }
}