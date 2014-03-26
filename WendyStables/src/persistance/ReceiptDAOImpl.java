package persistance;

import entity.Receipt;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReceiptDAOImpl implements  ReceiptDAO {

    private static final Logger logger = Logger.getLogger(ReceiptDAOImpl.class);
    private static ReceiptDAO service;
    private Connection c;
    private DBconnection dbcon;

    private PreparedStatement createStmt;
    private PreparedStatement findStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;

    private static final String db_URL = "jdbc:hsqldb:hsql://localhost:9001/xdb";
    private static final String db_USR = "SA";
    private static final String db_PWD = "";

    public ReceiptDAOImpl() {
        dbcon = DBconnection.instance();
        c = dbcon.getConnection();

        try {
            findStmt = c.prepareStatement("SELECT * FROM box");

        } catch (SQLException e) {
            logger.info("exception during BoxServicePrepareStatement");
            e.printStackTrace();
        }
    }

    public static ReceiptDAO initialize() {
        if( service == null) service = new ReceiptDAOImpl();
        return service;
    }

    @Override
    public void create(Receipt r) {

    }

    @Override
    public ObservableList<Receipt> find(Receipt r) {
        return null;
    }

    @Override
    public void update(Receipt r) {

    }

    @Override
    public void delete(Receipt r) {

    }
}
