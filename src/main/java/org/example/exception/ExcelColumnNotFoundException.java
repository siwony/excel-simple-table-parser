package org.example.exception;

import java.util.List;

public class ExcelColumnNotFoundException extends RuntimeException {

    private final List<String> cellNotFoundColumns;

    public ExcelColumnNotFoundException(List<String> cellNotFoundColumns) {
        super("엑셀 컬럼을 찾을 수 없습니다. " + String.join(", ", cellNotFoundColumns));
        this.cellNotFoundColumns = cellNotFoundColumns;
    }
}
