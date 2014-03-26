package service.impl;

import entity.Receipt;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class ReceiptServiceImpl implements ReceiptService {

    private static final Logger logger = Logger.getLogger(ReceiptServiceImpl.class);

    private PreparedStatement createStmt;
    private PreparedStatement findStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;

    public ReceiptServiceImpl(String url, String usr, String pw) {

        try {
            Connection c = DriverManager.getConnection(url, usr, pw);

            createStmt = c.prepareStatement("INSERT INTO receipt(customername, boxid, reservationid, dailyrate, totalcharge) " + "VALUES (?, ?, ?, ?, ?)");
            findStmt   = c.prepareStatement("SELECT * FROM receipt "  + "WHERE reservationid = ?");
//      updateStmt = ;
//      deleteStmt = ;


        } catch (Exception e) {
            logger.info("exception during connection in ReceiptServiceImpl");
            e.printStackTrace();
        }
    }

    @Override
    public void create(Receipt r) {
        logger.info("preparing create statement for a new receipt");

        try {
            createStmt.setString(1, r.getCustomerName());
            createStmt.setInt(2, r.getBoxID());
            createStmt.setInt(3, r.getReservationID());
            createStmt.setInt(4, r.getDailyRate());
            createStmt.setInt(5, r.getTotalCharge());
            createStmt.executeUpdate();
        } catch (Exception e) {
            logger.info("exception during the receipt DB creation");
            e.printStackTrace();
        }
        logger.info("New receipt should be created in the DB");
    }

    @Override
    public List<Receipt> find(Receipt r) {
        return null;
    }

    @Override
    public void update(Receipt r) {

    }

    @Override
    public void delete(Receipt r) {

    }
}
