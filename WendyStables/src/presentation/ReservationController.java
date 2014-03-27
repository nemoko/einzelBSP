package presentation;

import entity.Box;
import entity.BoxReservation;
import entity.Reservation;
import exception.ReservationException;
import javafx.collections.FXCollections;
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
    private int start_day;
    private int start_month;
    private int start_year;
    private int end_day;
    private int end_month;
    private int end_year;
    @FXML
    private Set<BoxReservation> clickedBox;
    @FXML
    private TableView<BoxReservation> tabulka;
    @FXML
    private TextField tf_customer;
    @FXML
    private TextField tf_horseName;
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
    private String from;
    private String end;
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
    private Label e_customer;
    @FXML
    private Label e_horse;

    @FXML
    public void onActionHomePage() {
        MainController.setWindow("Welcome.fxml");
    }

    public void removeErrors() {
        e_to_date.setVisible(false);
        e_from_date.setVisible(false);
        e_customer.setVisible(false);
        e_horse.setVisible(false);
        f_select_stable.setVisible(false);
        e_from_date.setVisible(false);
        e_to_date.setVisible(false);
        success_message.setVisible(false);
        error_message.setVisible(false);
        e_daily_int.setVisible(false);
        e_daily_positive.setVisible(false);
        e_size_int.setVisible(false);
        e_size_positive.setVisible(false);
        e_floor_choose.setVisible(false);
        e_window_choose.setVisible(false);
        e_outside_choose.setVisible(false);
    }

    @FXML
    public void onActionCreateReservation() {
        removeErrors();

        logger.info("onReserveBoxClicked");

        if(clickedBox == null || clickedBox.size() == 0) {
            f_select_stable.setVisible(true);
            return;
        }

        for(BoxReservation b : clickedBox) {

            Reservation r = new Reservation();

            try {

                //TODO can me removed?
                try {
                    r.setBoxID(b.getId());
                } catch (NullPointerException e) {
                    f_select_stable.setVisible(true);
                    return;
                }

                //TODO check if customerName < 45
                if(tf_customer.getText().equals("")) {
                    e_customer.setText("Enter a name");
                    e_customer.setVisible(true);
                } else {
                    if(tf_customer.getText().length() <=45 ) r.setCustomerName(tf_customer.getText());
                    else {
                        e_customer.setText("maximum 45 chars");
                        e_customer.setVisible(true);
                    }
                }

                //TODO check if horseName < 45
                if(tf_horseName.getText().equals("")) {
                    e_horse.setText("Enter a name");
                    e_horse.setVisible(true);
                } else {
                    if(tf_horseName.getText().length() <= 45) r.setHorseName(tf_horseName.getText());
                    else {
                        e_horse.setText("maximum 45 chars");
                        e_horse.setVisible(true);
                    }
                }


                //FROM DATE
                try {
                    //CHECK IF INT
                    start_day = Integer.parseInt(tf_start_day.getText());
                    start_month = Integer.parseInt(tf_start_month.getText());
                    start_year = Integer.parseInt(tf_start_year.getText());

                    //CHECK IF VALUE >= 0
                    if(Integer.signum(start_day) <= 0 || Integer.signum(start_month) <= 0 || Integer.signum(start_year) <= 0) {
                        e_from_date.setVisible(true);
                        e_from_date.setText("incorrect date format");
                    }

                    //CHECK IF DATE MATCHES MONTH
                    if((start_day == 31 && (start_month == 1 || start_month == 3 || start_month == 5 || start_month == 7 || start_month == 8 || start_month == 10 || start_month == 12))
                    || (start_day <= 30 && (start_month >= 1 && start_month <= 12))) {
                        //WHEN FEB, CHECK if LEAP YEAR
                        if(start_month == 2 && checkLeapYear(start_year)) {
                            if(start_day <= 29);
                            else {
                                e_from_date.setVisible(true);
                                e_from_date.setText("incorrect date, leap year");
                            }
                        }

                        //WHEN FEB, CHECK if not LEAP YEAR
                        if(start_month == 2 && !checkLeapYear(start_year)) {
                            if(start_day <28);
                            else {
                                e_from_date.setVisible(true);
                                e_from_date.setText("incorrect date, not a leap year");
                            }
                        }

                    } else {
                        e_from_date.setVisible(true);
                        e_from_date.setText("incorrect date format");

                    }

                    if(!e_from_date.isVisible()) {
                        from = start_year + "-" + start_month + "-" + start_day;
                        logger.info(from);
                    }

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

                //UNTIL DATE
                try {
                    //CHECK IF INT
                    end_day = Integer.parseInt(tf_end_day.getText());
                    end_month = Integer.parseInt(tf_end_month.getText());
                    end_year = Integer.parseInt(tf_end_year.getText());

                    //CHECK IF VALUE >= 0
                    if(Integer.signum(end_day) <= 0 || Integer.signum(end_month) <= 0 || Integer.signum(end_year) <= 0) {
                        e_to_date.setVisible(true);
                        e_to_date.setText("incorrect date format");
                    }

                    //CHECK IF DATE MATCHES MONTH
                    if((start_day == 31 && (start_month == 1 || start_month == 3 || start_month == 5 || start_month == 7 || start_month == 8 || start_month == 10 || start_month == 12))
                    || (start_day <= 30 && (start_month >= 1 && start_month <= 12))) {

                        //WHEN FEB, CHECK if LEAP YEAR
                        if(end_month == 2 && checkLeapYear(end_year)) {
                            if(end_day <= 29);
                            else {
                                e_to_date.setVisible(true);
                                e_to_date.setText("incorrect date, leap year");
                            }
                        }

                        //WHEN FEB, CHECK if not LEAP YEAR
                        if(end_month == 2 && !checkLeapYear(end_year)) {
                            if(end_day <28);
                            else {
                                e_to_date.setVisible(true);
                                e_to_date.setText("incorrect date, not a leap year");
                            }
                        }

                    } else {
                        e_to_date.setVisible(true);
                        e_to_date.setText("incorrect date format");

                    }

                    if(!e_to_date.isVisible()) {
                        end = end_year + "-" + end_month + "-" + end_day;
                        logger.info(end);
                    }

                } catch (NumberFormatException nfe) {
                    if(tf_end_day.getText().equals("")  || tf_end_month.getText().equals("") || tf_end_year.getText().equals("") ) {
                        e_to_date.setVisible(true);
                        e_to_date.setText("incorrect date format");
                    }if((tf_end_day.getText().equals("")  && tf_end_month.getText().equals("") && tf_end_year.getText().equals("") )) {
                        e_to_date.setVisible(true);
                        e_to_date.setText("enter date");
                    } else {
                        e_to_date.setVisible(true);
                        e_to_date.setText("enter date");
                    }
                }

                if((start_year <= end_year) && (start_month <= end_month) && (start_day <= end_day)) {
                    r.setStart(Date.valueOf(from));
                    r.setEnd(Date.valueOf(end));
                } else {
                    e_from_date.setText("FROM");
                    e_from_date.setVisible(true);
                    e_to_date.setText("TO");
                    e_to_date.setVisible(true);
                    return;
                }

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

    public void refreshTable() {
        BoxReservation b = new BoxReservation();

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

            BoxService bx = new BoxServiceImpl().initialize();
            tabulka.setItems(bx.find(b));
        } catch (Exception e) {
            logger.info("Exception refreshing table");
            e.printStackTrace();
        }
    }

    public void initializeBoxTable() {
        TableColumn<BoxReservation, Integer> id = new TableColumn<BoxReservation,Integer>("#");
        id.setMinWidth(7);
        id.setCellValueFactory(new PropertyValueFactory<BoxReservation, Integer>("id"));

        TableColumn<BoxReservation, Integer> dailyrate = new TableColumn<BoxReservation,Integer>("Daily Rate");
        dailyrate.setMinWidth(80);
        dailyrate.setCellValueFactory(new PropertyValueFactory<BoxReservation, Integer>("dailyRate"));

//        TableColumn<Box, String> picURL = new TableColumn<Box,String>("pic");
//        picURL.setMinWidth(60);
//        picURL.setCellValueFactory(new PropertyValueFactory<Box, String>("picURL"));

        TableColumn<BoxReservation, Integer> size = new TableColumn<BoxReservation,Integer>("size");
        size.setMinWidth(40);
        size.setCellValueFactory(new PropertyValueFactory<BoxReservation, Integer>("size"));

        TableColumn<BoxReservation, String> floor = new TableColumn<BoxReservation,String>("floor");
        floor.setMinWidth(60);
        floor.setCellValueFactory(new PropertyValueFactory<BoxReservation, String>("floor"));

        TableColumn<BoxReservation, Integer> window = new TableColumn<BoxReservation,Integer>("window");
        window.setMinWidth(60);
        window.setCellValueFactory(new PropertyValueFactory<BoxReservation, Integer>("window"));

        TableColumn<BoxReservation, Integer> outside = new TableColumn<BoxReservation,Integer>("outside");
        outside.setMinWidth(60);
        outside.setCellValueFactory(new PropertyValueFactory<BoxReservation, Integer>("outside"));

        TableColumn<BoxReservation, String> horseName = new TableColumn<BoxReservation,String>("<enter horse>");
        horseName.setMinWidth(148);
        horseName.setCellValueFactory(new PropertyValueFactory<BoxReservation, String>("horseName"));

        tabulka.getColumns().addAll(id,dailyrate,size,floor,window,outside,horseName);
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

        BoxReservation b = new BoxReservation();
        String exception = "";

//        ObservableList<Box> olist = null;

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
                success_message.setText("Displaying all stables");
                success_message.setVisible(true);

//                error_message.setText("Already displaying all stables");
//                error_message.setVisible(true);
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
            BoxService bx = new BoxServiceImpl().initialize();
            tabulka.setItems(bx.find(b));

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
//TODO method

        BoxReservation b = new BoxReservation();
        BoxService bx = new BoxServiceImpl().initialize();
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

//    public ObservableList<BoxReservation> boxItemsToBR(Box b) {
//
//        BoxService bx = BoxServiceImpl.initialize();
//        ObservableList<Box> alist = bx.find(b);
//        ObservableList<BoxReservation> blist = FXCollections.observableArrayList();
//
//        for (Box krabicka : alist) {
//            BoxReservation br = new BoxReservation();
//            b = krabicka;
//
//            br.setId(b.getId());
//            br.setDailyRate(b.getDailyRate());
//            br.setSize(b.getSize());
//            br.setFloor(b.getFloor());
//            br.setWindow(b.isWindow());
//            br.setOutside(b.isOutside());
//            br.setDeleted(b.isDeleted());
//            br.setPicURL(b.getPicURL());
//            br.setHorseName("");
//
//            blist.add(br);
//        }
//        return blist;
//    }

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
        clickedBox = new HashSet<BoxReservation>(tabulka.getSelectionModel().getSelectedItems());
//TODO do we need this?
        reserveButton.setDisable(false);
    }
}
