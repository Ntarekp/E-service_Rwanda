package org.example.csv;

import java.io.IOException;
import java.util.List;

public interface CSVReader<T> {
    List<T> readCSV(String filePath) throws IOException;
}
