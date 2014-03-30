package presentation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import persistance.DBconnection;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    private static final Logger logger = Logger.getLogger(WelcomeController.class);

    @FXML
    public Button mainMenuButton1;
    @FXML
    public Button mainMenuButton2;
    @FXML
    public Button mainMenuButton3;

    @FXML
    public void onActionReservation() {
        MainController.setWindow("Reservation.fxml");
    }

    @FXML
    public void onActionManageBox() {
        MainController.setWindow("ManageBox.fxml");
    }

    @FXML
    public void onActionPrintReceipt() {
        MainController.setWindow("PrintReceipt.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DBconnection dbConnection = new DBconnection();
            dbConnection.checkDriver();
            dbConnection.createConnection();
            logger.info("DB started");
        } catch (SQLException e) {
            logger.info("Exception during DB connection creation");

            mainMenuButton1.setDisable(true);
            mainMenuButton2.setDisable(true);
            mainMenuButton3.setDisable(true);


            final Stage dialogStage = new Stage();

            Button ok = new Button("Ok");
            ok.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialogStage.close();
                }
            });

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(VBoxBuilder.create().
                    children(new Text("\n\tDatabase is down.\n\nPlease Start the database.\n\n"), ok).
                    alignment(Pos.CENTER).padding(new Insets(25)).build()));
            dialogStage.showAndWait();
        }
    }
}