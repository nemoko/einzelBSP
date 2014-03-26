package application;

import entity.Box;
import entity.Reservation;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

    private static final Logger logger = Logger.getLogger(MainFrameController.class);

    @FXML
    private Box clicked;
    @FXML
    private TableView<Box> tabulka;
    @FXML
    private TextField tf_customer;
    @FXML
    private TextField tf_horseName;
    @FXML
    private TextField tf_start;
    @FXML
    private TextField tf_end;
    @FXML
    private Button reserveButton;
    @FXML
    private Button unReserveButton;
    @FXML
    private TextField tf_filter_id;
    private String filter_id_value;
    @FXML
    private Label error_input_date;
    @FXML
    private Label error_filter_id;

    @FXML
    public void onActionHomePage() {
        MainController.setWindow("Hybrid.fxml");
    }

    @FXML
    public void onActionCreateReservation() {
        logger.info("onReserveBoxClicked");

        Reservation r = new Reservation();

        r.setBoxID(clicked.getId());
        r.setCustomerName(tf_customer.getText());
        r.setHorseName(tf_horseName.getText());
        r.setStart(Date.valueOf(tf_start.getText()));
        r.setEnd(Date.valueOf(tf_end.getText()));
        r.setBoxID(clicked.getId());
        r.setDailyCharge(clicked.getDailyRate());

        MainController.reservationService.create(r);
    }

    @FXML
    public void onActionCancelReservation() {

    }

    public void refreshTable() {
        Box b = new Box();

        ObservableList<Box> olist = null;

        try {

            if(filter_id_value != null && !filter_id_value.trim().equals("")) {
                try {
                    b.setId(Integer.parseInt(filter_id_value));
                    error_filter_id.setVisible(false);
                } catch (Exception e) {
                    error_filter_id.setVisible(true);
                }
            }

            olist = (ObservableList<Box>) MainController.boxService.findByExample(b);
            tabulka.setItems(olist);

        } catch (Exception e) {
            logger.info("Exception refreshing table");
            e.printStackTrace();
        }
    }

    public void initializeTable() {
        TableColumn<Box, Integer> id = new TableColumn<Box,Integer>("ID");
        id.setMinWidth(60);
        id.setCellValueFactory(new PropertyValueFactory<Box, Integer>("id"));

        TableColumn<Box, Integer> dailyrate = new TableColumn<Box,Integer>("dailyrate");
        dailyrate.setMinWidth(60);
        dailyrate.setCellValueFactory(new PropertyValueFactory<Box, Integer>("dailyRate"));

        TableColumn<Box, String> picURL = new TableColumn<Box,String>("pic");
        picURL.setMinWidth(60);
        picURL.setCellValueFactory(new PropertyValueFactory<Box, String>("picURL"));

        TableColumn<Box, Integer> size = new TableColumn<Box,Integer>("size");
        size.setMinWidth(60);
        size.setCellValueFactory(new PropertyValueFactory<Box, Integer>("size"));

        TableColumn<Box, String> floor = new TableColumn<Box,String>("floor");
        floor.setMinWidth(60);
        floor.setCellValueFactory(new PropertyValueFactory<Box, String>("floor"));

        TableColumn<Box, Integer> window = new TableColumn<Box,Integer>("window");
        window.setMinWidth(60);
        window.setCellValueFactory(new PropertyValueFactory<Box, Integer>("window"));

        TableColumn<Box, Integer> outside = new TableColumn<Box,Integer>("outside");
        outside.setMinWidth(60);
        outside.setCellValueFactory(new PropertyValueFactory<Box, Integer>("outside"));

        tabulka.getColumns().addAll(id,picURL,dailyrate,size,floor,window,outside);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabulka.setItems(MainController.boxService.find());

        initializeTable();

        tf_filter_id.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                filter_id_value = tf_filter_id.getText();
                refreshTable();
            }
        });
    }

    @FXML
    public void mouseClick(MouseEvent arg0) {
        clicked = tabulka.getSelectionModel().getSelectedItem();
        reserveButton.setDisable(false);
        unReserveButton.setDisable(false);
    }
}
