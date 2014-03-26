package presentation;

import javafx.fxml.FXML;

public class WelcomeController {

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

}