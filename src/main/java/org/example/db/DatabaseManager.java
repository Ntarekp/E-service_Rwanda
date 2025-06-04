package org.example.db;

import org.example.data.NIDAIdentityData;
import org.example.data.RRATaxData;
import org.example.data.RSSBSocialSecurityData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseManager {
    private String url ="jdbc:mysql://localhost:3600/eservice";
    private String user ="root";
    private String password ="kaitare";

    public void insertRRATaxData(List<RRATaxData> data) throws SQLException {
        String sql = "INSERT INTO rra_tax_data (taxpayer_id, name,tax_amount,payment_status) VALUES (?,?,?,?)";
        try(Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                for(RRATaxData item :data){
                    pstmt.setString(1, item.getTaxpayerId());
                    pstmt.setString(2,item.getName());
                    pstmt.setDouble(3,item.getTaxAmount());
                    pstmt.setString(4, item.getPaymentStatus());
                    pstmt.addBatch();

                }
                pstmt.executeBatch();
        }
    }
    public void insertNIDAIdentityData(List<NIDAIdentityData> data)throws SQLException{
        String sql = "INSERT INTO nida_identity_data(national_id, full_name, date_of_birth, address) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(url,user,password);
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            for(NIDAIdentityData item: data){
                pstmt.setString(1, item.getNationalId());
                pstmt.setString(2,item.getFullName());
                pstmt.setDate(3,java.sql.Date.valueOf(item.getDateOfBirth()));
                pstmt.setString(4, item.getAddress());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }
    public void insertRSSBSocialSecurityData(List<RSSBSocialSecurityData> data) throws SQLException {
        String sql = "INSERT INTO rssb_social_security_data (employee_id, employer_name, contribution_amount) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (RSSBSocialSecurityData item : data) {
                pstmt.setString(1, item.getEmployeeId());
                pstmt.setString(2, item.getEmployeeName());
                pstmt.setDouble(3, item.getContributionAmount());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }
}