package service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import org.apache.log4j.Logger;
import entity.Box;

public class BoxServiceImpl implements BoxService {

    private static final Logger logger = Logger.getLogger(BoxServiceImpl.class);

    private PreparedStatement createStmt;
    private PreparedStatement findStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;

    public BoxServiceImpl(String url, String usr, String pw) {

        try {
            Connection c = DriverManager.getConnection(url, usr, pw);

            createStmt = c.prepareStatement("INSERT INTO box(size, window, picurl, strawfloor, outside, horsename, dailyrate, deleted) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            findStmt   = c.prepareStatement("SELECT * FROM box "  + "WHERE reservationid = ?");
//      updateStmt = ;
//      deleteStmt = ;

        } catch (Exception e) {
            logger.info("exception during connection in BoxServiceImpl");
            e.printStackTrace();
        }
    }

    @Override
    public void create(Box b) {
        logger.info("Preparing create statement for a new box");

        try {
            createStmt.setInt(1, b.getSize());
            createStmt.setBoolean(2, b.isWindow());
            createStmt.setString(3, b.getPicURL());
            createStmt.setBoolean(4, b.isStrawFloor());
            createStmt.setBoolean(5, b.isOutside());
            createStmt.setString(6, b.getHorseName());
            createStmt.setInt(7, b.getDailyRate());
            createStmt.setBoolean(8, b.isDeleted());
            createStmt.executeUpdate();
        } catch (Exception e) {
            logger.info("exception during Box DB creation");
            e.printStackTrace();
        }
        logger.info("New box should be created in the DB");
    }

    @Override
    public List<Box> find(Box b) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Box b) {
        // TODO Auto-generated method stub
    }

    @Override
    public void delete(Box b) {
        // TODO Auto-generated method stub
    }
}
