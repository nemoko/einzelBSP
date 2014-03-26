package service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

            createStmt = c.prepareStatement("INSERT INTO box(dailyrate, picurl, size, floor, window, outside) VALUES (?, ?, ?, ?, ?, ?)");
            findStmt   = c.prepareStatement("SELECT * FROM box WHERE reservationid = ?");
//      updateStmt = ;
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
