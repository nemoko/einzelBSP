package presentation;

import entity.Box;
import entity.Reservation;
import exception.ReservationException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;
import javafx.scene.control.TextField;
import service.BoxService;
import service.BoxServiceImpl;
import service.ReservationService;
import service.ReservationServiceImpl;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

    private static final Logger logger = Logger.getLogger(ReservationController.class);

    @FXML
    private Box clickedBox;
    @FXML
    private Reservation clickedRes;
    @FXML
    private TableView<Box> tabulka;
    @FXML
    private TableView<Reservation> resbulka;
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
    private TextField tf_filter_id;
    private String filter_id_value;
    @FXML
    private Label e_from_date;
    @FXML
    private Label e_to_date;
    @FXML
    private Label error_filter_id;
    @FXML
    private Label f_select_stable;

    @FXML
    public void onActionHomePage() {
        MainController.setWindow("Welcome.fxml");
    }

    public void removeErrors() {
        e_from_date.setVisible(false);
        e_to_date.setVisible(false);
    }

    @FXML
    public void removeSuccessMessage() {
        f_select_stable.setVisible(false);
    }

    @FXML
    public void onActionCreateReservation() {
        removeErrors();

        logger.info("onReserveBoxClicked");

        Reservation r = new Reservation();

        try {
            r.setBoxID(clickedBox.getId());
        } catch (NullPointerException e) {
            f_select_stable.setVisible(true);
            return;
        }

        r.setCustomerName(tf_customer.getText());
        r.setHorseName(tf_horseName.getText());
        r.setStart(Date.valueOf(tf_start.getText()));
        r.setEnd(Date.valueOf(tf_end.getText()));
        r.setBoxID(clickedBox.getId());
        r.setDailyCharge(clickedBox.getDailyRate());

        try {
            ReservationServiceImpl.initialize().create(r);
        } catch (ReservationException re) {
            re.getMessage();
        } catch (IllegalArgumentException ie) {
            e_from_date.setVisible(true);
            e_to_date.setVisible(true);
        }
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

            olist = BoxServiceImpl.initialize().find(b);
            tabulka.setItems(olist);
        } catch (Exception e) {
            logger.info("Exception refreshing table");
            e.printStackTrace();
        }
    }

    public void initializeBoxTable() {
        TableColumn<Box, Integer> id = new TableColumn<Box,Integer>("#");
        id.setMinWidth(7);
        id.setCellValueFactory(new PropertyValueFactory<Box, Integer>("id"));

        TableColumn<Box, Integer> dailyrate = new TableColumn<Box,Integer>("Daily Rate");
        dailyrate.setMinWidth(80);
        dailyrate.setCellValueFactory(new PropertyValueFactory<Box, Integer>("dailyRate"));

//        TableColumn<Box, String> picURL = new TableColumn<Box,String>("pic");
//        picURL.setMinWidth(60);
//        picURL.setCellValueFactory(new PropertyValueFactory<Box, String>("picURL"));

        TableColumn<Box, Integer> size = new TableColumn<Box,Integer>("size");
        size.setMinWidth(40);
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

        tabulka.getColumns().addAll(id,dailyrate,size,floor,window,outside);
    }

    public void initializeResTable() {
        TableColumn<Reservation, String> customername = new TableColumn<Reservation, String>("Customer Name");
        customername.setMinWidth(120);
        customername.setCellValueFactory(new PropertyValueFactory<Reservation, String>("customerName"));

        TableColumn<Reservation, String> horsename = new TableColumn<Reservation, String>("Horse Name");
        horsename.setMinWidth(100);
        horsename.setCellValueFactory(new PropertyValueFactory<Reservation, String>("horseName"));

        TableColumn<Reservation, Date> start = new TableColumn<Reservation, Date>("From");
        start.setMinWidth(50);
        start.setCellValueFactory(new PropertyValueFactory<Reservation, Date>("start"));

        TableColumn<Reservation, Date> until = new TableColumn<Reservation, Date>("To");
        until.setMinWidth(50);
        until.setCellValueFactory(new PropertyValueFactory<Reservation, Date>("until"));

        resbulka.getColumns().addAll(customername,horsename, start, until);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Box b = new Box();

        BoxService bx = BoxServiceImpl.initialize();
        tabulka.setItems(bx.find(b));

        initializeBoxTable();

        Reservation r = new Reservation();
        ReservationService rs = ReservationServiceImpl.initialize();
        resbulka.setItems(rs.find(r));

        initializeResTable();

//        tf_filter_id.setOnKeyReleased(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                filter_id_value = tf_filter_id.getText();
//                refreshTable();
//            }
//        });
    }

    @FXML
    public void mouseClick(MouseEvent arg0) {
        clickedBox = tabulka.getSelectionModel().getSelectedItem();
        clickedRes = resbulka.getSelectionModel().getSelectedItem();

        reserveButton.setDisable(false);
    }
}