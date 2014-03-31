package persistance;

import entity.Receipt;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

import java.sql.*;

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
        try {
            c = dbcon.getConnection();
        } catch (SQLException e) {
            logger.info("DB connection failed");
        }
    }

    public static ReceiptDAO initialize() {
        if( service == null) service = new ReceiptDAOImpl();
        return service;
    }

    @Override
    public Receipt create(Receipt r) {
        Receipt receipt = new Receipt();

        try {
            createStmt = c.prepareStatement("INSERT INTO receipt (totalcharge) " + "VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            createStmt.setInt(1, r.getTotalCharge());

            createStmt.executeUpdate();
            logger.info("New receipt should be created in DB");

            ResultSet rset = createStmt.getGeneratedKeys();

            rset.next();
            receipt.setId(rset.getInt("id"));

            logger.info("Receipt ID " + receipt.getId() + " was created");

            createStmt.close();
            rset.close();

            return receipt;
        } catch (SQLException e) {
            logger.info("exception during ReceiptDAO creation");
            e.printStackTrace();
            return null;
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
