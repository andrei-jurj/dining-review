package com.aj.diningreview.exporter;

import com.aj.diningreview.model.User;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserCsvExporter extends AbstractExporter{
    public void export(List<User> userList, HttpServletResponse httpServletResponse) throws IOException {
        super.setResponseHeader(httpServletResponse, "text/csv", ".csv");

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