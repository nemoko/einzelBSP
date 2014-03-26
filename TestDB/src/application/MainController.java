package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    static StackPane window;

    private static MainController mc;

    static void setWindow(String fxml) {
        try {
            window.getChildren().setAll( (Node) FXMLLoader.load(mc.getClass().getResource(fxml)) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mc = this;
    }
}
