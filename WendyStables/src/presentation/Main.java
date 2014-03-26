package presentation;

import javafx.scene.layout.BorderPane;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import org.apache.log4j.Logger;
import persistance.DBconnection;

public class Main extends Application {
	
	private static final Logger logger = Logger.getLogger(Main.class);

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root,800,800);

            MainController.setWindow("Welcome.fxml");
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

        DBconnection dbConnection = new DBconnection();
        dbConnection.checkDriver();
        dbConnection.createConnection();

        //javaFX
		logger.info("Application starting...");
		
		launch(args);
	}
}
