package org.example.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvDataProvider {

    /**
     * 从 CSV 文件读取所有行（跳过表头）
     * @param filePath 资源文件路径（如 "loginData.csv"）
     * @return 二维数组，每行包含各列值
     */
    public static List<String[]> readAll(String filePath) {
        List<String[]> records = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(
                CsvDataProvider.class.getClassLoader().getResource(filePath).getFile()))) {
            String[] header = reader.readNext();  // 跳过表头
            if (header == null) return records;
            String[] line;
            while ((line = reader.readNext()) != null) {
                records.add(line);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return records;
    }
}