package persistance;

import entity.Reservation;
import exception.ReservationException;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservationDAOImpl implements  ReservationDAO {

    private static final Logger logger = Logger.getLogger(ReservationDAOImpl.class);
    private static ReservationDAO service;
    private Connection c;
    private DBconnection dbcon;

    private PreparedStatement createStmt;
    private PreparedStatement findStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;

    private static final String db_URL = "jdbc:hsqldb:hsql://localhost:9001/xdb";
    private static final String db_USR = "SA";
    private static final String db_PWD = "";

    public ReservationDAOImpl() {
        dbcon = DBconnection.instance();
        c = dbcon.getConnection();

        try {
            createStmt = c.prepareStatement("INSERT INTO reservation(customername, horsename, start, until, dailyCharge, boxid) " + "VALUES (?, ?, ?, ?, ?, ?)");

        } catch (SQLException e) {
            logger.info("exception during BoxServicePrepareStatement");
            e.printStackTrace();
        }
    }

    public static ReservationDAO initialize() {
        if( service == null) service = new ReservationDAOImpl();
        return service;
    }

    @Override
    public void create(Reservation r) throws ReservationException {
        logger.info("Preparing create statement for a new reservation");

        try {
            createStmt.setString(1, r.getCustomerName());
            createStmt.setString(2, r.getHorseName());
            createStmt.setDate(3, r.getStart());
            createStmt.setDate(4, r.getEnd());
            createStmt.setInt(5, r.getDailyCharge());
            createStmt.setInt(6, r.getBoxID());

            createStmt.executeUpdate();
            logger.info("New reservation should be created in the DB");
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            logger.info("DATE CONSTRAINT VIOLATED");
            throw new ReservationException("Date constraint");

        } catch(SQLException e) {
            logger.info("SQL exception during Reservation DB creation");
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Reservation> find(Reservation r) {
        return null;
    }

    @Override
    public void update(Reservation r) {

    }

    @Override
    public void delete(Reservation r) {

    }
}

