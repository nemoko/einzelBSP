package presentation;

import entity.Box;
import entity.Reservation;
import exception.ReservationException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ReservationController implements Initializable {

    private static final Logger logger = Logger.getLogger(ReservationController.class);

    @FXML
    private Label success_message;
    @FXML
    private TextField tf_start_day;
    @FXML
    private TextField tf_start_month;
    @FXML
    private TextField tf_start_year;
    @FXML
    private TextField tf_end_day;
    @FXML
    private TextField tf_end_month;
    @FXML
    private TextField tf_end_year;
    @FXML
    private Set<Box> clickedBox;
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
    private TextField tf_daily_rate;
    @FXML
    private TextField tf_size;
    @FXML
    private CheckBox ch_window;
    @FXML
    private CheckBox ch_outside;
    @FXML
    private Button b_filter;
    @FXML
    private Button b_clearall;
    @FXML
    private ComboBox<String> cb_floor;
    private String floor_type;

    @FXML
    private Label error_message;
    @FXML
    private Label e_daily_int;
    @FXML
    private Label e_daily_positive;
    @FXML
    private Label e_size_int;
    @FXML
    private Label e_size_positive;
    @FXML
    private Label e_floor_choose;
    @FXML
    private Label e_window_choose;
    @FXML
    private Label e_outside_choose;
    @FXML
    private Label f_box_failed;
    @FXML
    private Label f_box_created;
    @FXML
    private Label f_box_updated;
    @FXML
    private Label f_box_deleted;
    @FXML
    private Label f_filter_failed;
    @FXML
    private Label f_filter_applied;
    @FXML
    private Label f_filter_criteria;

    @FXML
    public void onActionHomePage() {
        MainController.setWindow("Welcome.fxml");
    }

    public void removeErrors() {
        f_select_stable.setVisible(false);
        e_from_date.setVisible(false);
        e_to_date.setVisible(false);
        success_message.setVisible(false);
        error_message.setVisible(false);
    }

    @FXML
    public void onActionCreateReservation() {
        removeErrors();

        logger.info("onReserveBoxClicked");

        if(clickedBox == null || clickedBox.size() == 0) {
            f_select_stable.setVisible(true);
            return;
        }

        for(Box b : clickedBox) {

            Reservation r = new Reservation();

            //TODO can me removed?
            try {
                r.setBoxID(b.getId());
            } catch (NullPointerException e) {
                f_select_stable.setVisible(true);
                return;
            }

            try {
                //TODO check if customerName < 45
                r.setCustomerName(tf_customer.getText());
                //TODO check if horseName < 45
                r.setHorseName(tf_horseName.getText());


                //TODO FIX THIS BLOCK
                try {
                    Integer.parseInt(tf_start_day.getText());
                    Integer.parseInt(tf_start_month.getText());
                    Integer.parseInt(tf_start_year.getText());
                } catch (NumberFormatException nfe) {
                    if(tf_start_day.getText().equals("")  || tf_start_month.getText().equals("") || tf_start_year.getText().equals("") ) {
                        e_from_date.setVisible(true);
                        e_from_date.setText("incorrect date format");
                    }if((tf_start_day.getText().equals("")  && tf_start_month.getText().equals("") && tf_start_year.getText().equals("") )) {
                        e_from_date.setVisible(true);
                        e_from_date.setText("enter date");
                    } else {
                        e_from_date.setVisible(true);
                        e_from_date.setText("enter date");
                    }
                }

                try {
                    Integer.parseInt(tf_end_day.getText());
                    Integer.parseInt(tf_end_month.getText());
                    Integer.parseInt(tf_end_year.getText());
                } catch (NumberFormatException nfe) {
                    if(tf_end_day.getText().equals("") || tf_end_month.getText().equals("") || tf_end_year.getText().equals("") ) {
                        e_to_date.setVisible(true);
                        e_to_date.setText("enter date");
                    } else {
                        e_to_date.setVisible(true);
                        e_to_date.setText("incorrect date format");
                    }
                }

                if(checkLeapYear(Integer.parseInt(tf_start_year.getText()))) {

                } else {

                }

                if(checkLeapYear(Integer.parseInt(tf_end_year.getText()))) {

                } else {

                }


                r.setStart(Date.valueOf(tf_start.getText()));
                r.setEnd(Date.valueOf(tf_end.getText()));
                r.setBoxID(b.getId());
                r.setDailyCharge(b.getDailyRate());

                ReservationServiceImpl.initialize().create(r);
            } catch (ReservationException re) {
                re.getMessage();
            } catch (IllegalArgumentException ie) {
                logger.info("ILLEGAL ARGUMENT EXCEPTION");
                e_from_date.setVisible(true);
                e_to_date.setVisible(true);
            }
        }
    }

    public boolean checkLeapYear(int year) {

        if((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
            return true;
        } else return false;

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

    @FXML
    public void selectedWindow() {
        if(ch_outside.isDisabled()) ch_outside.setDisable(false);
        else ch_outside.setDisable(true);
    }

    @FXML
    public void selectedOutside() {
        if(ch_window.isDisabled()) ch_window.setDisable(false);
        else ch_window.setDisable(true);

    }

    @FXML
    public void onActionFilterTable() {
        removeErrors();

        Box b = new Box();
        String exception = "";

        ObservableList<Box> olist = null;

        if(!tf_daily_rate.getText().isEmpty()) { //if NOT EMPTY
            try {
                if(Integer.signum(Integer.parseInt(tf_daily_rate.getText())) == -1) { //if -INT
                    e_daily_positive.setVisible(true);
                    exception += "0";
                } else {
                    b.setDailyRate(Integer.parseInt(tf_daily_rate.getText())); //add daily rate to find criteria
                }
            } catch (NumberFormatException ne) { //if STRING
                e_daily_int.setVisible(true);
                exception += "0";
            }
        } else exception += "0"; //if EMPTY

        if(!tf_size.getText().isEmpty()) { //if field is not empty
            try {
                if(Integer.signum(Integer.parseInt(tf_size.getText())) == -1) {
                    e_size_positive.setVisible(true); //if int is negative, show error
                    exception += "1";
                } else {
                    b.setSize(Integer.parseInt(tf_size.getText())); //add size to find criteria
                }
            } catch (NumberFormatException ne) { //if string entered instead of int, show error
                e_size_int.setVisible(true);
                exception += "1";
            }
        } else exception += "1"; //if EMPTY

        try {
            if(cb_floor.getValue().isEmpty()) exception += "2"; //if no floor type, add to exception string -> decide if any criteria is selected
            else b.setFloor(floor_type);
        } catch (NullPointerException e) {
            exception += "2";
        }

        if(!ch_window.isSelected()) exception += "3";
        if(!ch_outside.isSelected()) exception += "4";

        b.setPicURL("");

        if(exception.contains("01234")) { //displaying alls
            if(clickedBox == null) {
                error_message.setText("Already displaying all stables");
                error_message.setVisible(true);
            } else {
                error_message.setText("No filter criteria set");
                error_message.setVisible(true);
            }
            //return;
        } else {
            //if neither window or outside is selected, ignore criterium
            if(exception.contains("3") && exception.contains("4")) {

            } else {
                b.setWindow(ch_window.isSelected());
                b.setOutside(ch_outside.isSelected());
            }
        }

        try {
            olist = BoxServiceImpl.initialize().find(b);
            tabulka.setItems(olist);
            if(!error_message.isVisible()) {
                success_message.setText("Filter applied");
                success_message.setVisible(true);
            }
        } catch (Exception e) {
            logger.info("Exception refreshing table");
            e.printStackTrace();
            error_message.setText("EXCEPTION");
            error_message.setVisible(true);
        }
    }

    @FXML
    public void removeFeedback() {
        error_message.setVisible(false);
        success_message.setVisible(false);
        f_select_stable.setVisible(false);
    }

    @FXML
    public void onActionClearAll() {

        tf_start_day.setText("");
        tf_start_month.setText("");
        tf_start_year.setText("");
        tf_end_day.setText("");
        tf_end_month.setText("");
        tf_end_year.setText("");
        tf_daily_rate.setText("");
        tf_customer.setText("");
        tf_horseName.setText("");
        tf_size.setText("");
        cb_floor.setValue("");
        ch_window.setSelected(false);
        ch_window.setDisable(false);
        ch_outside.setSelected(false);
        ch_outside.setDisable(false);

        removeErrors();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Box b = new Box();

        BoxService bx = BoxServiceImpl.initialize();
        tabulka.setItems(bx.find(b));

        initializeBoxTable();

        cb_floor.getItems().addAll("Sawdust","Straw","any");
        cb_floor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (cb_floor.getValue() != null) floor_type = cb_floor.getValue();
            }
        });

//        tf_filter_id.setOnKeyReleased(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                filter_id_value = tf_filter_id.getText();
//                refreshTable();
//            }
//        });
    }

    @FXML
    public void onActionClearBox() {
        tf_daily_rate.setText("");
        tf_size.setText("");
        cb_floor.setValue("");
        ch_window.setSelected(false);
        ch_window.setDisable(false);
        ch_outside.setSelected(false);
        ch_outside.setDisable(false);
    }

    @FXML
    public void mouseClick(MouseEvent arg0) {
        tabulka.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //TODO implement double selector
        clickedBox = new HashSet<Box>(tabulka.getSelectionModel().getSelectedItems());
        //clickedBox = tabulka.getSelectionModel().getSelectedItem();

        reserveButton.setDisable(false);
    }
}
