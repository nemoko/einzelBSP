package test;

import org.junit.After;
import org.junit.Before;
import persistance.BoxDAO;
import persistance.BoxDAOImpl;
import persistance.DBconnection;

import java.sql.Connection;

public class JDBCBoxDaoImplTest extends BoxDAOImplTest {

    private Connection c = null;
    private DBconnection db = null;

    @Before
    public void setUp() throws Exception
    {
        //open DB connection
        db = DBconnection.instance();
        db.checkDriver();
        db.createConnection();
        c = db.getConnection();
        c.setAutoCommit(false);

        //initialise new DAO service object
        BoxDAO bxDAO = new BoxDAOImpl().initialize();
        this.initBoxDAO(bxDAO);
    }

    @After
    public void tearDown() throws Exception
    {
        //close DB connection
        c.rollback();
        c.setAutoCommit(true);
//        db.closeConnection();
    }

}
