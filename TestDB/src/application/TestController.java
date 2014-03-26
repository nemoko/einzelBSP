package application;


import javafx.fxml.FXML;

public class TestController {

    @FXML
    public void onActionHomePage() {
        MainController.setWindow("Hybrid.fxml");
    }

}