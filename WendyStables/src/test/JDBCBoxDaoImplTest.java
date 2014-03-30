package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import persistance.BoxDAO;
import persistance.BoxDAOImpl;
import persistance.DBconnection;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCBoxDaoImplTest extends BoxDAOImplTest {

    private Connection c = null;
    private DBconnection db = null;

    @Before
    public void setUp()
    {
        //open DB connection
        try {
            db = DBconnection.instance();
            db.checkDriver();
            db.createConnection();
            c = db.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //initialise new DAO service object
        BoxDAO bxDAO = new BoxDAOImpl().initialize();
        this.initBoxDAO(bxDAO);
    }

    @After
    public void tearDown() throws Exception
    {
        //close DB connection
        db.closeConnection();
    }

}
