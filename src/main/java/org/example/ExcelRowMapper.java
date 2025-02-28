package org.example;

import org.apache.poi.ss.usermodel.Row;

import java.util.Map;

@FunctionalInterface
public interface ExcelRowMapper<T> {
    /**
     * 한 줄(Row)을 받아서, T 객체로 매핑한다.
     * columnIndexMap은 "컬럼 헤더명" -> "해당 셀 인덱스"의 Map이다.
     */
    T mapRow(Row row, Map<String, Integer> columnIndexMap);
}
