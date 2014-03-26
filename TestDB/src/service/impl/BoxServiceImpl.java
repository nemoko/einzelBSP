package service.impl;

import java.sql.*;
import java.util.List;

import entity.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import entity.Box;

public class BoxServiceImpl implements BoxService {

    private static final Logger logger = Logger.getLogger(BoxServiceImpl.class);

    private PreparedStatement createStmt;
    private PreparedStatement findStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement reserveStmt;
    private PreparedStatement deleteStmt;
    private Connection c;

    public BoxServiceImpl(String url, String usr, String pw) {

        try {
            c = DriverManager.getConnection(url, usr, pw);

            createStmt = c.prepareStatement("INSERT INTO box(dailyrate, picurl, size, floor, window, outside) VALUES (?, ?, ?, ?, ?, ?)");
            findStmt   = c.prepareStatement("SELECT * FROM box");
            //updateStmt = c.prepareStatement("UPDATE box set reservationID = ? WHERE id = ?");
            //reserveStmt= c.prepareStatement("UPDATE box set reservationID = ? WHERE id = ?");
            deleteStmt = c.prepareStatement("UPDATE box set deleted = true WHERE id = ?");

        } catch (Exception e) {
            logger.info("exception during connection in BoxServiceImpl");
            e.printStackTrace();
        }
    }

    @Override
    public void create(Box b) {
        logger.info("Preparing create statement for a new box");

        try{
            createStmt.setInt(1, b.getDailyRate());
            createStmt.setString(2, b.getPicURL());
            createStmt.setInt(3, b.getSize());
            createStmt.setString(4, b.whatFloor());
            createStmt.setBoolean(5, b.isWindow());
            createStmt.setBoolean(6, b.isOutside());

            createStmt.executeUpdate();
        } catch (SQLException e) {
            logger.info("Exception during box statement creation");
            e.printStackTrace();
        }

        logger.info("New box should be created in the DB");
    }

    @Override
    public ObservableList<Box> find() {
        ObservableList<Box> olist = FXCollections.observableArrayList();

        try {
            ResultSet rset = findStmt.executeQuery();
            while(rset.next()) {
                Box b = new Box();
                b.setId(rset.getInt("id"));
                b.setDailyRate(rset.getInt("dailyrate"));
                b.setPicURL(rset.getString("picurl"));
                b.setSize(rset.getInt("size"));
                b.setFloor(rset.getString("floor"));
                b.setWindow(rset.getBoolean("window"));
                b.setOutside(rset.getBoolean("outside"));
                b.setDeleted(rset.getBoolean("deleted"));

                olist.add(b);
            }
        } catch (SQLException e) {
            logger.info("exception during box find statement");
            e.printStackTrace();
        }
        return olist;
    }


    public ObservableList<Box> findByExample(Box b) {
        ObservableList<Box> olist = FXCollections.observableArrayList();
        String query = "select * from box ";
        String where = "";

        if(b.getId() != null) {
            where = "WHERE ID = " + b.getId() + ";";

        }

        try {
            PreparedStatement ps = c.prepareStatement(query + where);
            ResultSet rset = ps.executeQuery();

            Box box = new Box();

            while(rset.next()) {
                box.setId(rset.getInt("id"));
                box.setDailyRate(rset.getInt("dailyrate"));
                box.setPicURL(rset.getString("picurl"));
                box.setSize(rset.getInt("size"));
                box.setFloor(rset.getString("floor"));
                box.setWindow(rset.getBoolean("window"));
                box.setOutside(rset.getBoolean("outside"));
                box.setDeleted(rset.getBoolean("deleted"));

                olist.add(box);
            }


        } catch (SQLException e) {
            logger.info("exception during box find statement");
            e.printStackTrace();
        }
        return olist;
    }

       @Override
       public void update(Box b) {
         logger.info("reservation created");
//
//        try {
//            updateStmt.setInt(1, b.getReservationID());
//            updateStmt.setInt(2, b.getId());
//            updateStmt.executeUpdate();
//        } catch (SQLException e) {
//            logger.info("reservation failed");
//            e.printStackTrace();
//        }
        }

    @Override
    public void reserve(Reservation r) {
        logger.info("reservation created");

        try {
            reserveStmt.setInt(1, r.getId());;
            reserveStmt.setInt(2, r.getBoxID());
            reserveStmt.executeUpdate();
        } catch (SQLException e) {
            logger.info("reservation failed");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Box b) {
        logger.info("Preparing delete statement for a box");

        try {
            deleteStmt.setInt(1, b.getId());
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            logger.info("exception during BOX DB deletion");
            e.printStackTrace();
        }
    }
}
