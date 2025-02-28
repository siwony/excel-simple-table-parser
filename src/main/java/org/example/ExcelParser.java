package org.example;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.exception.ExcelColumnNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ExcelParser {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ExcelParser.class);

    /**
     * 전체 파싱 로직: Workbook 생성 -> Sheet 선택 -> Header 찾기 -> 내용 파싱 -> 결과 리턴
     */
    public <T> List<T> parseJavaObj(
        final InputStream inputStream,
        final int sheetIndex,
        final Set<String> columns,
        final int headerNumber,
        final ExcelRowMapper<T> rowMapper
    ) {
        try (inputStream) {
            final var workbook = new XSSFWorkbook(inputStream);

            final var sheet = workbook.getSheetAt(sheetIndex);
            final var headerRow = sheet.getRow(headerNumber);
            final var columnIndexMap = getColumnIndexMap(headerRow, columns);

            final var parsedDataList = new ArrayList<T>();
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    logger.debug("Row is null, skip row idx = {}", rowIndex);
                    continue;
                }

                try {
                    T data = rowMapper.mapRow(row, columnIndexMap);
                    parsedDataList.add(data);
                } catch (Exception e) {
                    logger.error("Failed to parse row idx: {}", rowIndex, e);
                }
            }
            return parsedDataList;
        } catch (IOException e) {
            logger.error("Excel parsing error " , e);
            throw new RuntimeException("Excel parsing error", e);
        }
    }

    /**
     * 헤더(Row)에서 각 헤더 컬럼이 몇 번째 셀에 있는지 매핑 정보를 만든다.
     * Set<String> columns에 담긴 컬럼이 헤더에 존재하는지 검증한다.
     * 누락된 컬럼이 있으면 예외(ExcelColumnNotFoundException) 발생.
     */
    private Map<String, Integer> getColumnIndexMap(final Row headerRow, final Set<String> columns) {
        final Map<String, Integer> columnIndexMap = new HashMap<>();

        // 헤더에서 컬럼 인덱스 매핑
        for (Cell cell : headerRow) {
            String columnName = cell.getStringCellValue();
            columnIndexMap.put(columnName, cell.getColumnIndex());
        }

        // 필요한 컬럼들 중에서 누락된 것이 있으면 예외 던짐
        List<String> notFoundList = columns.stream()
            .filter(col -> !columnIndexMap.containsKey(col))
            .toList();
        if (!notFoundList.isEmpty()) {
            throw new ExcelColumnNotFoundException(notFoundList);
        }

        return columnIndexMap;
    }

}
