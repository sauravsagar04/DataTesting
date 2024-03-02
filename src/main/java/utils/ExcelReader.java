package utils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    private static XSSFSheet sheet;
    private static XSSFWorkbook workbook = null;
    static File file;
    static FileInputStream inputStream;

    public static Sheet getSheet(String filepath) throws IOException {
        String bp = System.getProperty("user.dir");
        file = new File(bp + filepath);
        inputStream = new FileInputStream(file);
        workbook = new XSSFWorkbook(inputStream);
        sheet = workbook.getSheet("Worksheet"); // Sheet name of Excel file
        return sheet;
    }

    public static int getRowCount(String filepath) throws IOException {
        Sheet sheet = getSheet(filepath);
        if (sheet != null) {
            int rowCount = 0;
            for (Row row : sheet) {
                if (row != null && !isEmptyRow(row)) {
                    rowCount++;
                }
            }
            return rowCount-1;
        } else {
            return 0;
        }
    }

    public static int getColumnCount(String filepath) throws IOException {
        Sheet sheet = getSheet(filepath);
        if (sheet != null) {
            int maxColumnCount = 0;
            for (Row row : sheet) {
                if (row != null) {
                    int lastCellNum = row.getLastCellNum();
                    if (lastCellNum > maxColumnCount) {
                        maxColumnCount = lastCellNum;
                    }
                }
            }
            return maxColumnCount;
        } else {
            return 0;
        }
    }

    private static boolean isEmptyRow(Row row) {
        for (int i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
            if (row.getCell(i) != null && row.getCell(i).toString().trim().length() > 0) {
                return false;
            }
        }
        return true;
    }
}




