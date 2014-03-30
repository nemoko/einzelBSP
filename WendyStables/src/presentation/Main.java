package presentation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import javafx.application.Application;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
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
			Scene scene = new Scene(root,900,850);

            MainController.setWindow("Welcome.fxml");
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

        try {
            DBconnection dbConnection = new DBconnection();
            dbConnection.checkDriver();
            dbConnection.createConnection();
            logger.info("DB started");
        } catch (Exception e) {
            logger.info("DB ERROR");
            //TODO throw popup
        }

        //javaFX
		logger.info("Application starting...");
		
		launch(args);
	}
}
