package persistance;

import entity.Box;
import entity.BoxReservation;
import entity.Receipt;
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
        try {
        c = dbcon.getConnection();
        } catch (SQLException e) {
            logger.info("DB connection failed");
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
            createStmt = c.prepareStatement("INSERT INTO reservation(customername, horsename, start, until, dailyCharge, boxid) " + "VALUES (?, ?, ?, ?, ?, ?)");

            createStmt.setString(1, r.getCustomerName());
            createStmt.setString(2, r.getHorseName());
            createStmt.setDate(3, r.getStart());
            createStmt.setDate(4, r.getEnd());
            createStmt.setInt(5, r.getDailyCharge());
            createStmt.setInt(6, r.getBoxID());

            createStmt.executeUpdate();
            logger.info("New reservation should be created in the DB");

            createStmt.close();
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

            rset.close();
            ps.close();
        } catch (SQLException e) {
            logger.info("exception during reservation find statement");
            e.printStackTrace();
        }
        return olist;
    }

    @Override
    public ObservableList<Reservation> findActiveBox(Reservation r) {
        ObservableList<Reservation> olist = FXCollections.observableArrayList();
        String query = "select * from reservation ";
        String where = "WHERE ";

        if(r.getBoxID() != null) where += "boxid = '" + r.getBoxID() + "' AND ";

        where += "r.until > CURDATE()) AND ";
        where += "PAYED = FALSE;";

        String temp = query + where;

        try {
            PreparedStatement ps = c.prepareStatement(temp);
            ResultSet rset = ps.executeQuery();

            while(rset.next()) {
                Reservation rr = new Reservation();

                rr.setId(rset.getInt("id"));

                olist.add(rr);
            }

            rset.close();
            ps.close();
        } catch (SQLException e) {
            logger.info("box has no active reservations");
        }

        return olist;
    }

    public ObservableList<Reservation> findCustomer(Reservation r) {
        ObservableList<Reservation> olist = FXCollections.observableArrayList();


        String query = "select * from reservation r ";
        String where = "WHERE ";
        String customer = "customername LIKE '%" + r.getCustomerName() + "%'";
        String timeConstraint = "AND (r.start >= '" + r.getStart() + "' AND r.until <= '"  + r.getEnd() + "')";

        String temp;

        if(r.getEnd() != null && r.getStart() != null) temp = query + where + customer + timeConstraint;
        else temp = query + where + customer;

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

            rset.close();
            ps.close();
        } catch (SQLException e) {
            logger.info("exception during reservation find statement");
            e.printStackTrace();
        }

        return olist;
    }

    //PAY for a reservation
    @Override
    public void update(Reservation r, Receipt rt) {
        logger.info("updating Reservation in DB");

        //        UPDATE Customers
        //        SET ContactName='Alfred Schmidt', City='Hamburg'
        //        WHERE CustomerName='Alfreds Futterkiste';

        String query = "UPDATE reservation ";
        String set   = "SET payed =true, receiptid='" + rt.getId() + "'";
        String where = " WHERE id='" + r.getId() + "';";

        String temp = query + set + where;

        try {
            PreparedStatement ps = c.prepareStatement(temp);
            ps.executeUpdate();

            logger.info("Reservation successfully updated");
            logger.info(ps.getGeneratedKeys());

            ps.close();
        } catch (SQLException e) {
            logger.info("exception during reservation update statement");
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Reservation r) {

    }
}

