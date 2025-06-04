package org.example.csv;

import org.example.data.RSSBSocialSecurityData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RSSBCSVReader implements CSVReader<RSSBSocialSecurityData> {
    @Override
    public List<RSSBSocialSecurityData> readCSV(String filePath) throws IOException {
        List<RSSBSocialSecurityData> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                RSSBSocialSecurityData data = new RSSBSocialSecurityData();
                data.setEmployeeId(values[0]);
                data.setEmployeeName(values[1]);
                data.setContributionAmount(Double.parseDouble(values[2]));
                dataList.add(data);
            }
        }
        return dataList;
    }
}