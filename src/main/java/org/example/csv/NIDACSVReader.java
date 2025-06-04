package org.example.csv;

import org.example.data.NIDAIdentityData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NIDACSVReader implements CSVReader<NIDAIdentityData> {
    @Override
    public List<NIDAIdentityData> readCSV(String filepath) throws IOException{
        List<NIDAIdentityData> dataList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filepath))){
            br.readLine();
            String line;
            while((line=br.readLine())!=null){
                String[] values = line.split(",");
                NIDAIdentityData data = new NIDAIdentityData();
                data.setNationalId(values[0]);
                data.setFullName(values[1]);
                data.setDateOfBirth(LocalDate.parse(values[2]));
                data.setAddress(values[3]);
                dataList.add(data);
            }
        }
        return  dataList;
    }
}
