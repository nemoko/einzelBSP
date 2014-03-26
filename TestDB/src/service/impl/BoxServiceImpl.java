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

    public BoxServiceImpl(String url, String user, String pw) throws SQLException, ClassNotFoundException {

        //starting DB
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (Exception e) {
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return;
        }

        Connection c = DriverManager.getConnection(url, user, pw);

//        createStmt = ;
//        findStmt = ;
//        updateStmt = ;
//        deleteStmt = ;
    }

    @Override
    public Box create(Box b) {



        logger.info("New Box should be created in the DB");
        return null;
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
