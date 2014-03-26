package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.apache.log4j.Logger;
import service.impl.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static final String db_URL = "jdbc:hsqldb:hsql://localhost:9001/xdb";
    private static final String db_USR = "SA";
    private static final String db_PWD = "";

    private static final Logger logger = Logger.getLogger(MainFrameController.class);

    public static final BoxService boxService = new BoxServiceImpl(db_URL,db_USR,db_PWD);
    public static final ReservationService reservationService = new ReservationServiceImpl(db_URL,db_USR,db_PWD);
    public static final ReceiptService receiptService = new ReceiptServiceImpl(db_URL,db_USR,db_PWD);
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
