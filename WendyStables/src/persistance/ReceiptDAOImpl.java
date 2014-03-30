package persistance;

import entity.Receipt;
import entity.Reservation;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Set;

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
            createStmt = c.prepareStatement("INSERT INTO receipt (totalcharge) " + "VALUES (?)", Statement.RETURN_GENERATED_KEYS);

        } catch (SQLException e) {
            logger.info("exception during ReceiptDAOPrepareStatement");
            e.printStackTrace();
        }

    }

    public static ReceiptDAO initialize() {
        if( service == null) service = new ReceiptDAOImpl();
        return service;
    }

    @Override
    public int create(Receipt r) {

        try {
            createStmt.setInt(1, r.getTotalCharge());

            createStmt.executeUpdate();
            logger.info("New receipt should be created in DB");

            ResultSet rset = createStmt.getGeneratedKeys();

            rset.next();
            int i = rset.getInt("id");

            logger.info("Receipt ID " + i + " was created");
            return i;

        } catch (SQLException e) {
            logger.info("exception during ReceiptDAO creation");
            e.printStackTrace();
            return -1;
        }
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
