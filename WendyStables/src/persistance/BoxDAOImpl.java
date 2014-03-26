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
        String where = "WHERE ";

        if(b.getDailyRate() != null) where += "dailyrate = '" + b.getDailyRate() + "' AND ";
        if(b.getSize() != null) where += "size = '" + b.getSize() + "' AND ";
        if(b.getFloor() != null && !b.getFloor().isEmpty() && !b.getFloor().contains("any")) where += "floor = '" + b.getFloor() + "' AND ";
        if(b.isWindow() != null && (b.isWindow() ^ b.isOutside())) where += "window = '" + b.isWindow() + "' AND ";
        if(b.isOutside() != null && (b.isWindow() ^ b.isOutside())) where += "outside = '" + b.isOutside() + "' AND ";

        where += "DELETED = FALSE;";

        String temp = query + where;

        try {
            PreparedStatement ps = c.prepareStatement(temp);
            ResultSet rset = ps.executeQuery();

            while(rset.next()) {
                Box box = new Box();

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
        logger.info("updating Box in DB");

        //        UPDATE Customers
        //        SET ContactName='Alfred Schmidt', City='Hamburg'
        //        WHERE CustomerName='Alfreds Futterkiste';

        String query = "UPDATE BOX ";
        String set   = "SET ";
        String where = " WHERE id='" + b.getId() + "';";

        if(b.getDailyRate() != null) set += "dailyrate = '" + b.getDailyRate() + "', ";
        if(b.getSize() != null) set += "size = '" + b.getSize() + "', ";
        if(b.getFloor() != null && !b.getFloor().isEmpty() && !b.getFloor().contains("any")) set += "floor = '" + b.getFloor() + "', ";
        if(b.isWindow() != null && (b.isWindow() ^ b.isOutside())) set += "window = '" + b.isWindow() + "', ";
        if(b.isOutside() != null && (b.isWindow() ^ b.isOutside())) set += "outside = '" + b.isOutside() + "', ";
        //PICTURE??

        String temp = query + set.substring(0, set.length()-2) + where;

        try {
            PreparedStatement ps = c.prepareStatement(temp);
            ps.executeUpdate();

            logger.info("Box successfully updated");
        } catch (SQLException e) {
            logger.info("exception during box update statement");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Box b) {
        String delete = "UPDATE BOX Set deleted = true where id ='" + b.getId() + "';";

        try {
            PreparedStatement ps = c.prepareStatement(delete);
            ps.executeUpdate();

            logger.info("box deleted");
        } catch (SQLException e) {
            logger.info("exception during box deletion");
            e.printStackTrace();
        }
    }
}
