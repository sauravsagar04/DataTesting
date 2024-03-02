package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectors {
    private static Connection conn;
    public Connection connectToMySql() throws ClassNotFoundException, SQLException {
        Class.forName(Util.getConstantProperty("mysql.class.name"));
        String url = Util.getConstantProperty("mySql_Url");
        String userName = Util.getConstantProperty("mySql_Username");
        String password = Util.getConstantProperty("mySql_Passwd");
        conn = DriverManager.getConnection(url, userName, password);
        return conn;
    }
}
