# How to use
To parse, you must implement ExcelRowMapper, which provides the column information you want to carce.

```java
public class SampleParser {

    private final static int SHEET_INDEX = 0;
    private final static int HEADER_ROW_INDEX = 0;

    public List<SampleRecord> parse() throws IOException {
        Path path = getResourceAsPath("sample.xlsx");
        InputStream inputStream = new FileInputStream(path.toFile());
        ExcelParser excelParser = new ExcelParser();

        return excelParser.parseJavaObj(
                inputStream,
                SHEET_INDEX,
                Set.of("ID", "Name", "Age", "Email"),
                HEADER_ROW_INDEX,
                (row, columnIndexMap) -> { // implement ExcelRowMapper by lambda
                    String name = row.getCell(columnIndexMap.get("Name")).getStringCellValue();
                    int age = (int) row.getCell(columnIndexMap.get("Age")).getNumericCellValue();
                    String email = row.getCell(columnIndexMap.get("Email")).getStringCellValue();
                    int id = (int) row.getCell(columnIndexMap.get("ID")).getNumericCellValue();
                    return new SampleRecord(id, name, age, email);
                }
        );
    }
...
}
```

and just use it!

```java
public static void main(String[] args) throws IOException {
    final var parser = new SampleParser();

    List<SampleRecord> parse = parser.parse();
    for (SampleRecord sampleRecord : parse) {
        System.out.println(sampleRecord);
    }
}
```
