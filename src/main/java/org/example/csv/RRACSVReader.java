package org.example.csv;

import org.example.data.RRATaxData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RRACSVReader implements CSVReader<RRATaxData> {
    @Override
public List<RRATaxData> readCSV(String filePath) throws IOException{
        List<RRATaxData> dataList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader((new FileReader(filePath)))){
            br.readLine();
            String line;
            while((line = br.readLine())!=null){
                String[] values = line.split(",");
                RRATaxData data = new RRATaxData();
                data.setTaxpayerId(values[0]);
                data.setName(values[1]);
                data.setTaxAmount(Double.parseDouble(values[3]));
                data.setPaymentStatus(values[3]);
                dataList.add(data);
            }
        }
        return dataList;
    }
}
