package persistance;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

    private static final Logger logger = Logger.getLogger(DBconnection.class);

    private static final String db_URL = "jdbc:hsqldb:hsql://localhost:9001/xdb";
    private static final String db_USR = "SA";
    private static final String db_PWD = "";

    private static DBconnection con;
    private static Connection c;

    public static DBconnection instance() {
        if (con == null) {
            con = new DBconnection();
        }
            return con;
    }

    public void checkDriver() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (Exception e) {
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
        }
    }

    public synchronized void createConnection() throws SQLException {
        c = DriverManager.getConnection(db_URL,db_USR,db_PWD);
    }

    public Connection getConnection() throws SQLException {
        if (c == null) {
            createConnection();
        }
        return c;
    }

    public void closeConnection() {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
