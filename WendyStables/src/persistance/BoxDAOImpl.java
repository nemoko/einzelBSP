package persistance;

import entity.Box;
import entity.Reservation;
import exception.BoxException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import persistance.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoxDAOImpl implements BoxDAO {

    private static final Logger logger = Logger.getLogger(BoxDAOImpl.class);
    private static BoxDAO service;
    private Connection c;
    private DBconnection dbcon;

    private PreparedStatement createStmt;
    private PreparedStatement findStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;

    private static final String db_URL = "jdbc:hsqldb:hsql://localhost:9001/xdb";
    private static final String db_USR = "SA";
    private static final String db_PWD = "";

    public BoxDAOImpl() {
        dbcon = DBconnection.instance();
        c = dbcon.getConnection();

        try {
            createStmt = c.prepareStatement("INSERT INTO box (dailyrate, picurl, size, floor, window, outside) " + "VALUES (?,?,?,?,?,?)");
            findStmt = c.prepareStatement("SELECT * FROM box");

        } catch (SQLException e) {
            logger.info("exception during BoxServicePrepareStatement");
            e.printStackTrace();
        }
    }

    public static BoxDAO initialize() {
        if( service == null) service = new BoxDAOImpl();
        return service;
    }

    @Override
    public void create(Box b) throws BoxException {
        logger.info("create received by persistance layer");

        try {
            createStmt.setInt(1, b.getDailyRate());
            createStmt.setString(2, b.getPicURL());
            createStmt.setInt(3,b.getSize());
            createStmt.setString(4,b.getFloor());
            createStmt.setBoolean(5,b.isWindow());
            createStmt.setBoolean(6,b.isOutside());

            createStmt.executeUpdate();

        } catch (SQLException e) {
            logger.info("exception during box DB creation");
            throw new BoxException("box is null");
        }
        logger.info("New box should be created in DB");
    }

    @Override
    public ObservableList<Box> find(Box b) {
        ObservableList<Box> olist = FXCollections.observableArrayList();
        String query = "select * from box ";
        String where = "";

        if(b.getId() != null) {
            where = "WHERE ID = " + b.getId() + ";";
        }

        try {
            PreparedStatement ps = c.prepareStatement(query + where);
            ResultSet rset = ps.executeQuery();

            while(rset.next()) {
                Box box = new Box();

                if(!rset.getBoolean("deleted")) {
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
            }
        } catch (SQLException e) {
            logger.info("exception during box find statement");
            e.printStackTrace();
        }
        return olist;
    }

    @Override
    public void update(Box b) {

    }

    @Override
    public void delete(Box b) {

    }
}
