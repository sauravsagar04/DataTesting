import testClass.MySql_CrimeReportTable;
import java.io.IOException;
import java.sql.*;
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {

        MySql_CrimeReportTable mysqlTest = new MySql_CrimeReportTable();
        mysqlTest.setUp();
        mysqlTest.testRowCount();
        mysqlTest.testColumnCount();
        mysqlTest.testNullValue();
        mysqlTest.testUniqueColumn();
        mysqlTest.testGenderConstant();
        mysqlTest.closeConn();

    }
}
