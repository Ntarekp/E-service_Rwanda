# E-service Backend Integration System

## Project Overview
This Java-based system integrates E-service with government agencies by:
1. Simulating agency data via CSV files
2. Processing CSV data using OOP principles
3. Storing data in a PostgreSQL database via JDBC
4. Implementing robust exception handling

## Project Structure
```
e-service-integration/
├── data/                   # CSV files from government agencies
│   ├── Immigration.csv
│   ├── RevenueAuthority.csv
│   └── LandRegistry.csv
├── src/
│   └── com/
│       └── eservice/
│           ├── model/      # Data model classes
│           │   └── AgencyData.java
│           ├── dao/        # Data access objects
│           │   ├── DatabaseManager.java
│           │   └── CSVProcessor.java
│           ├── exceptions/ # Custom exceptions
│           │   └── DataProcessingException.java
│           └── Main.java   # Entry point
├── lib/                    # JDBC driver
└── README.md
```

## Sample CSV Files
### Immigration.csv
```csv
id,passportNumber,applicantName,status,issueDate
1,P1001,Alice Johnson,APPROVED,2023-05-15
2,P1002,Bob Smith,PENDING,2023-06-20
3,P1003,Carol Williams,REJECTED,2023-07-10
```

### RevenueAuthority.csv
```csv
taxId,businessName,registrationDate,taxCategory,annualRevenue
TAX-001,ABC Enterprises,2022-01-15,A,15000000
TAX-002,XYZ Solutions,2021-11-30,B,8000000
```

## Java Implementation
### 1. Data Model Class
`src/com/eservice/model/AgencyData.java`
```java
package com.eservice.model;

import java.time.LocalDate;

public class AgencyData {
    private int id;
    private String passportNumber;
    private String applicantName;
    private String status;
    private LocalDate issueDate;
    
    public AgencyData(int id, String passportNumber, String applicantName, 
                      String status, LocalDate issueDate) {
        this.id = id;
        this.passportNumber = passportNumber;
        this.applicantName = applicantName;
        this.status = status;
        this.issueDate = issueDate;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getPassportNumber() { return passportNumber; }
    public void setPassportNumber(String passportNumber) { 
        this.passportNumber = passportNumber; 
    }
    
    // Additional getters/setters for other properties...
}
```

### 2. CSV Processor
`src/com/eservice/dao/CSVProcessor.java`
```java
package com.eservice.dao;

import com.eservice.model.AgencyData;
import com.eservice.exceptions.DataProcessingException;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CSVProcessor {
    public List<AgencyData> readCSV(Path filePath) throws DataProcessingException {
        List<AgencyData> records = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                AgencyData record = new AgencyData(
                    Integer.parseInt(values[0]),
                    values[1],
                    values[2],
                    values[3],
                    LocalDate.parse(values[4])
                );
                records.add(record);
            }
        } catch (Exception e) {
            throw new DataProcessingException("Error processing CSV: " + filePath, e);
        }
        return records;
    }
}
```

### 3. Database Manager
`src/com/eservice/dao/DatabaseManager.java`
```java
package com.eservice.dao;

import com.eservice.model.AgencyData;
import java.sql.*;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/eservice_db";
    private static final String USER = "admin";
    private static final String PASSWORD = "securepass";
    
    public void saveRecords(List<AgencyData> records) throws SQLException {
        String sql = "INSERT INTO agency_data (id, passport_number, applicant_name, status, issue_date) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (AgencyData record : records) {
                pstmt.setInt(1, record.getId());
                pstmt.setString(2, record.getPassportNumber());
                pstmt.setString(3, record.getApplicantName());
                pstmt.setString(4, record.getStatus());
                pstmt.setDate(5, Date.valueOf(record.getIssueDate()));
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }
}
```

### 4. Custom Exception
`src/com/eservice/exceptions/DataProcessingException.java`
```java
package com.eservice.exceptions;

public class DataProcessingException extends Exception {
    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### 5. Main Application
`src/com/eservice/Main.java`
```java
package com.eservice;

import com.eservice.dao.CSVProcessor;
import com.eservice.dao.DatabaseManager;
import com.eservice.exceptions.DataProcessingException;
import com.eservice.model.AgencyData;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        CSVProcessor processor = new CSVProcessor();
        DatabaseManager dbManager = new DatabaseManager();
        
        try {
            // Process Immigration Data
            Path csvPath = Paths.get("data", "Immigration.csv");
            List<AgencyData> records = processor.readCSV(csvPath);
            
            // Persist to Database
            dbManager.saveRecords(records);
            System.out.println(records.size() + " records imported successfully!");
            
        } catch (DataProcessingException | SQLException e) {
            System.err.println("Operation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

## Database Schema
```sql
CREATE DATABASE eservice_db;

CREATE TABLE agency_data (
    id SERIAL PRIMARY KEY,
    passport_number VARCHAR(20) NOT NULL,
    applicant_name VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL,
    issue_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Setup & Execution
### Prerequisites
- Java JDK 17+
- PostgreSQL 14+
- [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/download/)

### Compilation
```bash
javac -d bin src/com/eservice/**/*.java
```

### Execution
```bash
java -cp bin:lib/postgresql-42.6.0.jar com.eservice.Main
```

### Expected Output
```
3 records imported successfully!
```

## Key Features
1. **Batch Processing**: Efficient bulk database inserts
2. **Exception Handling**: Custom exceptions for CSV processing errors
3. **Resource Management**: Automatic resource closure with try-with-resources
4. **Data Validation**: Type conversion during CSV parsing
5. **Security**: Parameterized SQL queries to prevent injection
