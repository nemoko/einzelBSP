package application;

import javafx.fxml.FXML;

public class HybridController {

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