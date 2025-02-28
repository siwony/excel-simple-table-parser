package org.example.exception;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.util.function.Supplier;

public class ExcelCellParseFailException extends RuntimeException {

    private final static String DEFAULT_MESSAGE = "엑셀 셀 파싱에 실패했습니다.";

    private final int columnIndex;
    private final int rowIndex;
    private final CellType cellType;

    public ExcelCellParseFailException(int columnIdx, int rowIndex, CellType cellType, Supplier<String> messageSupplier, Throwable cause) {
        super(messageSupplier == null ? DEFAULT_MESSAGE
            : "%s %s 행: %d, 열: %d, 셀 데이터 타입: %s".formatted(DEFAULT_MESSAGE, messageSupplier.get(), columnIdx, rowIndex, cellType),
            cause
        );
        this.columnIndex = columnIdx;
        this.rowIndex = rowIndex;
        this.cellType = cellType;
    }

    public ExcelCellParseFailException(Cell cell, Supplier<String> messageSupplier, Throwable cause) {
        this(cell.getColumnIndex(), cell.getRowIndex(), cell.getCellType() ,messageSupplier, cause);
    }

    public ExcelCellParseFailException(Cell cell, Throwable cause) {
        this(cell, null, cause);
    }

    public ExcelCellParseFailException(Cell cell) {
        this(cell, null);
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public CellType getCellType() {
        return cellType;
    }
}
