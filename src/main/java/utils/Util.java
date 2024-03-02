package utils;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private Connection mysqlConnection;
    Connectors conn_instance = new Connectors();
    public static String getConstantProperty(String key) {
        Properties properties = new Properties();

        try (InputStream input = Util.class.getClassLoader().getResourceAsStream("connection_constants.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

    public static void insertQuery(Connection mysqlConnection,String QUERY_ID, String SUITE_START_AT, String EXECUTION_DATETIME, String SUITE_ID, String TEST_CASE_ID,
                                   String SRC_DB_NAME, String SRC_SCHEMA_NAME, String SRC_TABLE_NAME, String TRG_DB_NAME,
                                   String TRG_SCHEMA_NAME, String TRG_TABLE_NAME, String TRG_COLUMN, String TEST_CASE_STATUS) throws SQLException {
        PreparedStatement statement = mysqlConnection.prepareStatement("insert into test_output_table values('"+QUERY_ID+"','"+SUITE_START_AT+"','"+EXECUTION_DATETIME+"','"+SUITE_ID+"','"+TEST_CASE_ID+"','"+SRC_DB_NAME+"','"+SRC_SCHEMA_NAME+"','"+SRC_TABLE_NAME+"','"+TRG_DB_NAME+"','"+TRG_SCHEMA_NAME+"','"+TRG_TABLE_NAME+"','"+TRG_COLUMN+"','"+TEST_CASE_STATUS+"')");
        statement.executeUpdate();

//        "insert into test_output_table values('"+QUERY_ID+"','"+SUITE_START_AT+"','"+EXECUTION_DATETIME+"','"+SUITE_ID+"','"+TEST_CASE_ID+"','"+TEST_CASE_TYPE+"','"+SRC_DB_NAME+"','"+SRC_SCHEMA_NAME+"','"+SRC_TABLE_NAME+"','"+TRG_DB_NAME+"','"+TRG_SCHEMA_NAME+"','"+TRG_TABLE_NAME+"','"+TRG_COLUMN+"','"+TEST_CASE_STATUS+")"


    }

}
