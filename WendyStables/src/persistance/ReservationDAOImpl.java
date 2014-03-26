package persistance;

import entity.Box;
import entity.BoxReservation;
import entity.Reservation;
import exception.ReservationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        ObservableList<Reservation> olist = FXCollections.observableArrayList();
        String query = "select * from reservation ";
        String where = "WHERE ";

        if(!r.getCustomerName().isEmpty() && r.getCustomerName() != null) where += "customername = '" + r.getCustomerName() + "' AND ";
        if(!r.getHorseName().isEmpty() && r.getHorseName() != null) where += "horsename = '" + r.getHorseName() + "' AND ";
        if(r.getStart() != null) where += "start = '" + r.getStart() + "' AND ";
        if(r.getEnd() != null) where += "until = '" + r.getEnd() + "' AND ";
        if(r.getBoxID() != null) where += "boxid = '" + r.getBoxID() + "' AND ";
        if(r.getReceiptID() != null) where += "receiptid = '" + r.getReceiptID() + "' AND ";
        if(r.getDailyCharge() != null) where += "dailycharge = '" + r.getDailyCharge() + "' AND ";
//      if(r.getPayed() != null) where += "payed = '" + r.getPayed() + "' AND ";

        where += "PAYED = FALSE;";

        String temp = query + where;

        try {
            PreparedStatement ps = c.prepareStatement(temp);
            ResultSet rset = ps.executeQuery();

            while(rset.next()) {
                Reservation rr = new Reservation();

                rr.setId(rset.getInt("id"));
                rr.setCustomerName(rset.getString("customername"));
                rr.setHorseName(rset.getString("horsename"));
                rr.setStart(rset.getDate("start"));
                rr.setEnd(rset.getDate("until"));
                rr.setBoxID(rset.getInt("boxid"));
                rr.setDailyCharge(rset.getInt("dailycharge"));
                rr.setPayed(rset.getBoolean("payed"));

                olist.add(rr);
            }
        } catch (SQLException e) {
            logger.info("exception during reservation find statement");
            e.printStackTrace();
        }
        return olist;
    }

//    public ObservableList<BoxReservation> findBR(Reservation r) {
//        logger.info("BxoReservation finding in DB");
//
//        try {
//            //findBRStmt.setDate(1, r.getStart());
//
//            ResultSet rset = findBRStmt.executeQuery();
//
//            while(rset.next()) {
//
//            }
//
//        } catch(SQLException e) {
//            logger.info("exception during FINDBR in reservation");
//            e.printStackTrace();
//        }
//
//
//
//
//        return null;
//    }


    @Override
    public void update(Reservation r) {

    }

    @Override
    public void delete(Reservation r) {

    }
}

