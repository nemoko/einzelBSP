package service.impl;

import entity.Reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import org.apache.log4j.Logger;

public class ReservationServiceImpl implements ReservationService {

    private static final Logger logger = Logger.getLogger(ReservationServiceImpl.class);

    private PreparedStatement createStmt;
    private PreparedStatement findStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;

    public ReservationServiceImpl(String url, String usr, String pw) {

        try {
            Connection c = DriverManager.getConnection(url, usr, pw);

            createStmt = c.prepareStatement("INSERT INTO reservation(customername, horsename, start, end) " + "VALUES (?, ?, ?, ?)");
            findStmt   = c.prepareStatement("SELECT * FROM reservation "  + "WHERE customername = ?");
    //      updateStmt = ;
    //      deleteStmt = ;

        } catch (Exception e) {
            logger.info("exception during connection in ReservationServiceImpl");
            e.printStackTrace();
        }
    }

    @Override
    public void create(Reservation r) {
        logger.info("Preparing create statement for a new reservation");

        try {
            createStmt.setString(1, r.getCustomerName());
            createStmt.setString(2, r.getHorseName());
            createStmt.setDate(3, r.getStart());
            createStmt.setDate(4, r.getEnd());

            createStmt.executeUpdate();
        } catch (Exception e) {
            logger.info("exception during Reservation DB creation");
            e.printStackTrace();
        }
        logger.info("New reservation should be created in the DB");
    }

    @Override
    public List<Reservation> find(Reservation r) {
        return null;
    }

    @Override
    public void update(Reservation r) {

    }

    @Override
    public void delete(Reservation r) {

    }
}
