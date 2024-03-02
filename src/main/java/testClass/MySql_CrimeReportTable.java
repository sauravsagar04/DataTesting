package testClass;
import com.aventstack.extentreports.Status;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.*;


public class MySql_CrimeReportTable extends BaseClass {
    Timestamp suiteStartTime;
    Timestamp executionTime;
    public Connection mysqlConnection;
    Connectors conn_instance = new Connectors();

    @BeforeSuite
    public void setUp() throws SQLException, ClassNotFoundException {
        mysqlConnection = conn_instance.connectToMySql();
        Timestamp suiteStartTime = new Timestamp(System.currentTimeMillis());

    }

    @AfterSuite
    public void closeConn() throws SQLException {
        if (mysqlConnection != null && !mysqlConnection.isClosed()) {
            mysqlConnection.close();
        }
    }

    @BeforeMethod
    protected void startReporting(Method method) {
        super.startReporting(method); // Call startReporting method from the base class
    }

    @Test(enabled = true)
    public void testRowCount() throws SQLException, IOException {
        String excelFilePath = "\\src\\main\\src_file\\crime_report_Src_file.xlsx";

        //validating here
        if(verifyRowCount("crime_report",excelFilePath) == "PASS") {
            System.out.println("RowCount - PASS");
//            test.log(Status.PASS, "RowCount - PASS");
        }
        else{
            System.out.println("RowCount - FAIL");
            test.log(Status.FAIL, "RowCount - FAIL");
        }

    }

    @Test(enabled = true)
    public void testColumnCount() throws SQLException, IOException {
        String excelFilePath = "\\src\\main\\src_file\\crime_report_Src_file.xlsx";

        //validating here
        if(verifyColumnCount("crime_report",excelFilePath) == "PASS") {
            System.out.println("ColumnCount - PASS");
//            test.log(Status.PASS, "ColumnCount - PASS");
        }
        else{
            System.out.println("ColumnCount - FAIL");
            test.log(Status.FAIL, "ColumnCount - FAIL");
        }
}

    @Test(enabled = true)
    public void testNullValue() throws SQLException {

        if(verifyNullValueMySql("crime_report","DR_NO") == "PASS"){
            System.out.println("nullValueCount - PASS");
            test.log(Status.PASS, "nullValueCount - PASS");
        }
        else {
            System.out.println("nullValueCount - FAIL");
            test.log(Status.FAIL, "nullValueCount - FAIL");
        }
}

    @Test(enabled = true)
    public void testUniqueColumn() throws SQLException {

        if(verifyUniqueValueMySql("crime_report") == "PASS"){
            System.out.println("uniqueValueCount - PASS");
            test.log(Status.PASS, "uniqueValueCount - PASS");
        }
        else {
            System.out.println("uniqueValueCount - FAIL");
            test.log(Status.FAIL, "uniqueValueCount - FAIL");
        }
}

    @Test (enabled = true)
    public void testGenderConstant() throws SQLException {
        if(verifyGenderConstantMySql("crime_report") == "PASS"){
            System.out.println("genderConstantCount - PASS");
            test.log(Status.PASS, "genderConstantCount - PASS");
        }
        else {
            System.out.println("genderConstantCount - FAIL");
            test.log(Status.FAIL, "genderConstantCount - FAIL");
        }
 }
    private String verifyRowCount(String tableName,String filepath) throws SQLException, IOException {
        int sqlRowCount = 0;
        int excelRowCount;
        String queryResult;
        String query = "SELECT COUNT(*) FROM " + tableName;


        executionTime = new Timestamp(System.currentTimeMillis());
        PreparedStatement statement = mysqlConnection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            sqlRowCount = resultSet.getInt(1);
        }

        excelRowCount = ExcelReader.getRowCount(filepath);

        if(sqlRowCount == excelRowCount){
            queryResult =  "PASS";
        }
        else {
            queryResult =  "FAIL";
        }

        ExtentManager.testDescription = "Row count test case for - "+tableName;
        ExtentManager.executedQuery = query;
        ExtentManager.executedQueryResult = "SQL row Count ->"+sqlRowCount+" Excel row count -> "+excelRowCount;
        Util.insertQuery(mysqlConnection,tableName+executionTime, String.valueOf(suiteStartTime), String.valueOf(executionTime),"MySql_CrimeReportTable_suite","verifyRowCount",
                "SRC_DB_NAME","SRC_SCHEMA_NAME","crime_report","TRG_DB_NAME",
                "TRG_SCHEMA_NAME","crime_report_src","", queryResult);


        return queryResult;
    }

    private String verifyColumnCount(String tableName, String filepath) throws SQLException, IOException {
        int sqlColumnCount = 0;
        int excelColumnCount;
        String queryResult;
        String query = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"+tableName+"';";

        executionTime = new Timestamp(System.currentTimeMillis());
        PreparedStatement statement = mysqlConnection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next())
            sqlColumnCount =  resultSet.getInt(1);

        excelColumnCount = ExcelReader.getColumnCount(filepath);

        if(sqlColumnCount == excelColumnCount){
            System.out.println("Sql Column Count ->"+sqlColumnCount+" Excel column count -> "+excelColumnCount);
            queryResult = "PASS";
        }
        else {
            System.out.println("Sql Column Count ->"+sqlColumnCount+" Excel column count -> "+excelColumnCount);
            queryResult = "FAIL";
        }
        ExtentManager.testDescription = "Row count test case for - "+tableName;
        ExtentManager.executedQuery = query;
        ExtentManager.executedQueryResult = "SQLrowCount ->"+sqlColumnCount+" Excel row count -> "+excelColumnCount;
        Util.insertQuery(mysqlConnection,tableName+executionTime, String.valueOf(suiteStartTime), String.valueOf(executionTime),"MySql_CrimeReportTable_suite","verifyColumnCount",
                "SRC_DB_NAME","SRC_SCHEMA_NAME","crime_report","TRG_DB_NAME",
                "TRG_SCHEMA_NAME","crime_report_src","", queryResult);
        return queryResult;
    }
    private String verifyNullValueMySql(String tableName, String columnName) throws SQLException {
        String queryResult;
        int nullValueCount = 0;
        String query = "Select count(*) as cnt from " +tableName+ " where "+columnName+" is null";
        try (PreparedStatement statement = mysqlConnection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                nullValueCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(nullValueCount == 0 ) {
            queryResult = "PASS";
        }
        else {
            queryResult = "FAIL";
        }
        ExtentManager.testDescription = "Null value test case for - "+tableName;
        ExtentManager.executedQuery = query;
        ExtentManager.executedQueryResult = "null value Count ->"+nullValueCount;
        Util.insertQuery(mysqlConnection,tableName+executionTime, String.valueOf(suiteStartTime), String.valueOf(executionTime),"MySql_CrimeReportTable_suite","verifyNullValueCount",
                "SRC_DB_NAME","SRC_SCHEMA_NAME","crime_report","TRG_DB_NAME",
                "TRG_SCHEMA_NAME","crime_report_src","", queryResult);
        return queryResult;
    }

    private String verifyUniqueValueMySql(String tableName) throws SQLException {
        String queryResult;
        int uniqueValueCount = 0;
        String query = "Select count(*) from " +tableName+ " group by DR_NO,`Date Rptd`,`DATE OCC`,`TIME OCC`,AREA,`AREA NAME`,`Rpt Dist No`,`Part 1-2`,`Crm Cd`,`Crm Cd Desc`,Mocodes,`Vict Age`,`Vict Sex`,`Vict Descent`,`Premis Cd`,`Premis Desc`,`Weapon Used Cd`,`Weapon Desc`,Status,`Status Desc`,`Crm Cd 1`,`Crm Cd 2`,`Crm Cd 3`,`Crm Cd 4`,LOCATION,`Cross Street`,LAT,LON having count(*) > 1 ";
        try (PreparedStatement statement = mysqlConnection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                uniqueValueCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(uniqueValueCount == 0 ) {
            queryResult = "PASS";
        }
        else {
            queryResult = "FAIL";
        }

        ExtentManager.testDescription = "Unique value test case for - "+tableName;
        ExtentManager.executedQuery = query;
        ExtentManager.executedQueryResult = "unique value Count ->"+uniqueValueCount;
        Util.insertQuery(mysqlConnection,tableName+executionTime, String.valueOf(suiteStartTime), String.valueOf(executionTime),"MySql_CrimeReportTable_suite","verifyUniqueValueCount",
                "SRC_DB_NAME","SRC_SCHEMA_NAME","crime_report","TRG_DB_NAME",
                "TRG_SCHEMA_NAME","crime_report_src","", queryResult);

        return queryResult;
    }

    private String verifyGenderConstantMySql(String tableName) throws SQLException {
        String queryResult;
        int genderConstantCount = 0;
        String query = "Select count(*) from "+tableName+" where `Vict Sex` not in ('M','F')";
        try (PreparedStatement statement = mysqlConnection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                genderConstantCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(genderConstantCount == 0 ) {
            queryResult = "PASS";
        }
        else {
            queryResult = "FAIL";
        }
        ExtentManager.testDescription = "Unique value test case for - "+tableName;
        ExtentManager.executedQuery = query;
        ExtentManager.executedQueryResult = "unique value Count ->"+genderConstantCount;
        Util.insertQuery(mysqlConnection,tableName+executionTime, String.valueOf(suiteStartTime), String.valueOf(executionTime),"MySql_CrimeReportTable_suite","verifyGenderConstantCount",
                "SRC_DB_NAME","SRC_SCHEMA_NAME","crime_report","TRG_DB_NAME",
                "TRG_SCHEMA_NAME","crime_report_src","", queryResult);

        return queryResult;
    }
}

