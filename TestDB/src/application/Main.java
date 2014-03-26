package application;

import javafx.scene.layout.BorderPane;
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
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root,400,400);

            MainController.setWindow("Hybrid.fxml");
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

        //starting DB
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (Exception e) {
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return;
        }

        //javaFX
		logger.info("Application starting...");
		
		launch(args);
	}
}
