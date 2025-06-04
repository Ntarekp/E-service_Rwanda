package org.example;

import org.example.csv.CSVReader;
import org.example.csv.NIDACSVReader;
import org.example.csv.RRACSVReader;
import org.example.csv.RSSBCSVReader;
import org.example.data.NIDAIdentityData;
import org.example.data.RRATaxData;
import org.example.data.RSSBSocialSecurityData;
import org.example.db.DatabaseManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {
   public static void main(String[] args){
       try{
           CSVReader<RRATaxData> rraReader = new RRACSVReader();
           List<RRATaxData> rraData = rraReader.readCSV("data/rra_data.csv");

           CSVReader<NIDAIdentityData> nidaReader = new NIDACSVReader();
           List<NIDAIdentityData> nidaData =nidaReader.readCSV("data/nida_data.csv");

           CSVReader<RSSBSocialSecurityData> rssbReader = new RSSBCSVReader();
           List<RSSBSocialSecurityData> rssbData = rssbReader.readCSV("data/rssb_data.csv");


           //Save to database
           DatabaseManager dbManager = new DatabaseManager();
           dbManager.insertRRATaxData(rraData);
           dbManager.insertNIDAIdentityData(nidaData);
           dbManager.insertRSSBSocialSecurityData(rssbData);

           System.out.println("Data Successfully imported and sorted in the Database");

       } catch (IOException e){
           System.err.println("Error reading CSV file:" +e.getMessage()) ;
       }catch (SQLException e){
           System.err.println("Database error: "+e.getMessage());
       }
   }
}