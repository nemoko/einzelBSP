package application;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import service.impl.BoxServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main extends Application {
	
	private static final Logger logger = Logger.getLogger(Main.class);

    private static final String db_URL = "jdbc:hsqldb:hsql://localhost:9001/xdb";
    private static final String db_USR = "SA";
    private static final String db_PWD = "";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("MainFrame.fxml"));
			Scene scene = new Scene(root,400,400);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)  throws SQLException, ClassNotFoundException {

        //starting DB
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (Exception e) {
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return;
        }

        BoxServiceImpl boximpl = new BoxServiceImpl();


        Connection c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9001/xdb", "SA", "");

        //javaFX
		logger.info("Application starting...");
		
		launch(args);
	}
}
