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

    private static Connection c;

    private PreparedStatement createStmt;
    private PreparedStatement findStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;

    public BoxServiceImpl(String url, String user, String pw) {

        try {
            c = DriverManager.getConnection(url, user, pw);

            createStmt = c.prepareStatement("INSERT INTO box(size, window, picurl) " + "VALUES (?, ?, ?)");
            findStmt   = c.prepareStatement("SELECT size, window, picurl FROM box "  + "WHERE reservationid = ?");
//      updateStmt = ;
//      deleteStmt = ;
        } catch (Exception e) {
            logger.info("exception during connection");
        }

    }

    @Override
    public void create(Box b) {

        logger.info("Preparing create statement for a new box");
//
//        createStmt.setInt(1, b.getSize());
//        createStmt.setBoolean(2, b.isWindow());
//        createStmt.setString(3, b.getPicURL());
//
//        createStmt.executeUpdate();

        logger.info("New Box should be created in the DB");
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
