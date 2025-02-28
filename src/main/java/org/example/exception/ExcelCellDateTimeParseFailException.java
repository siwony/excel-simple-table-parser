package org.example.exception;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class ExcelCellDateTimeParseFailException extends ExcelCellParseFailException {

    private final static String DEFAULT_MESSAGE = "잘못된 날짜/시간 형식입니다.";
    private final String value;

    public ExcelCellDateTimeParseFailException(int columnIdx, int rowIndex, CellType cellType, String value, Throwable cause) {
        super(columnIdx, rowIndex, cellType, () -> DEFAULT_MESSAGE + " 셀 데이터: " + value, cause);
        this.value = value;
    }

    public ExcelCellDateTimeParseFailException(Cell cell, String value, Throwable cause) {
        this(cell.getColumnIndex(), cell.getRowIndex(), cell.getCellType(), value, cause);
    }

    public ExcelCellDateTimeParseFailException(Cell cell, String value) {
        this(cell, value, null);
    }
}
