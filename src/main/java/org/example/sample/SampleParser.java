package org.example.sample;

import org.example.ExcelParser;
import org.example.sample.model.SampleRecord;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;

public class SampleParser {

    public List<SampleRecord> parse() throws IOException {
        Path path = getResourceAsPath("sample.xlsx");
        InputStream inputStream = new FileInputStream(path.toFile());
        ExcelParser excelParser = new ExcelParser();

        return excelParser.parseJavaObj(
                inputStream,
                0,
                Set.of("ID", "Name", "Age", "Email"),
                0,
                (row, columnIndexMap) -> {
                    String name = row.getCell(columnIndexMap.get("Name")).getStringCellValue();
                    int age = (int) row.getCell(columnIndexMap.get("Age")).getNumericCellValue();
                    String email = row.getCell(columnIndexMap.get("Email")).getStringCellValue();
                    int id = (int) row.getCell(columnIndexMap.get("ID")).getNumericCellValue();
                    return new SampleRecord(id, name, age, email);
                }
        );
    }


    private InputStream getResourceAsStream(String fileName) {
        return SampleParser.class.getClassLoader().getResourceAsStream(fileName);
    }

    private Path getResourceAsPath(String fileName) throws IOException {
        InputStream inputStream = getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("Resource not found: " + fileName);
        }
        Path tempFile = Files.createTempFile("resource_", fileName);
        Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
        return tempFile;
    }
}
