package presentation;

import entity.Box;
import entity.Reservation;
import exception.ReservationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;
import javafx.scene.control.TextField;
import service.*;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class PrintReceiptController implements Initializable {

    private static final Logger logger = Logger.getLogger(PrintReceiptController.class);

    @FXML
    private Label feedback;
    @FXML
    private Set<Reservation> clickedRes;
    @FXML
    private Button b_clearall;
    @FXML
    private TableView<Reservation> resbulka;
    @FXML
    private TextField tf_customer;
    private String customerValue;
    @FXML
    private TextField tf_horseName;
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
    private String from;
    private String end;
    @FXML
    private Button pay;
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
    private String filename;

    @FXML
    public void onActionHomePage() {
        MainController.setWindow("Welcome.fxml");
    }

    @FXML
    public void removeSuccessMessage() {
        e_from_date.setVisible(false);
        e_to_date.setVisible(false);
    }

    @FXML
    public void removeFeedback() {
        feedback.setVisible(false);
    }

    @FXML
    public void onActionClearAll() {
        removeSuccessMessage();

        tf_start_day.setText("");
        tf_start_month.setText("");
        tf_start_year.setText("");
        tf_end_day.setText("");
        tf_end_month.setText("");
        tf_end_year.setText("");
    }

    @FXML
    public void onActionPay() {
        removeSuccessMessage();
        removeFeedback();

        String customer = "";

        if(clickedRes == null) {
            feedback.setText("Select reservations");
            feedback.setVisible(true);
            return;
        } else {
            //looping through customer names
            for (Reservation r : clickedRes) {

                if(customer.trim().isEmpty()) customer = r.getCustomerName();

                if(!customer.equals(r.getCustomerName())) {
                    feedback.setText("Invoices can be created for one customer at a time");
                    feedback.setVisible(true);
                    return;
                }
            }

            //if payable [end < today]
            for (Reservation r : clickedRes) {
                if(!checkIfPayable(r)) {
                    feedback.setText("Reservation is still active and cannot be payed for");
                    feedback.setVisible(true);
                    return;
                }
            }
        }
        //proceeding to pay
        logger.info("proceeding to pay");

        ReceiptService rc = new ReceiptServiceImpl();
        rc.create(clickedRes);


        Reservation r = new Reservation();
        ReservationService rs = ReservationServiceImpl.initialize();

        resbulka.setItems(rs.find(r));

            //mark all reservations as payed

            //create pop up with receipt info

    }

    //check if end < now
    public boolean checkIfPayable(Reservation r) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        logger.info(timeStamp);

        if(r.getEnd().compareTo(Date.valueOf(timeStamp)) <= 0) return true;
        else return false;
    }

    @FXML
    public void onActionFilter() {
        removeSuccessMessage();
        ObservableList<Reservation> olist = FXCollections.observableArrayList();
        Reservation r = new Reservation();

        try {
            if(validateDate()) {
                r.setStart(Date.valueOf(from));
                r.setEnd(Date.valueOf(end));
            }
        } catch (Exception ie) {
            logger.info("date validation failed");
            return;
        }

        ReservationService rs = new ReservationServiceImpl().initialize();
        olist = rs.findCustomer(r);
        resbulka.setItems(olist);
    }

    public boolean validateDate() {
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
            if((end_day == 31 && (end_month == 1 || end_month == 3 || end_month == 5 || end_month == 7 || end_month == 8 || end_month == 10 || end_month == 12))
                    || (end_day <= 30 && (end_month >= 1 && end_month <= 12))) {

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

//        if(e_from_date.isVisible() || e_to_date.isVisible()) return false;

        if((start_year <= end_year) && (start_month <= end_month) && (start_day <= end_day)) {
            return true;
        } else {
            e_from_date.setText("FROM");
            e_from_date.setVisible(true);
            e_to_date.setText("TO");
            e_to_date.setVisible(true);
            return false;
        }
    }

    public boolean checkLeapYear(int year) {
        if((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
            return true;
        } else return false;
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
        until.setCellValueFactory(new PropertyValueFactory<Reservation, Date>("end"));

        TableColumn<Reservation, Date> boxid = new TableColumn<Reservation, Date>("Stable");
        boxid.setMinWidth(50);
        boxid.setCellValueFactory(new PropertyValueFactory<Reservation, Date>("boxID"));

        TableColumn<Reservation, Date> dailycharge = new TableColumn<Reservation, Date>("Daily Charge");
        dailycharge.setMinWidth(120);
        dailycharge.setCellValueFactory(new PropertyValueFactory<Reservation, Date>("dailyCharge"));

        resbulka.getColumns().addAll(boxid, customername,horsename, start, until, dailycharge);
    }

//TODO refresh reservation table based on customer name primarily, and DATES if any entered
    public void refreshTable() {
        Reservation r = new Reservation();

        ObservableList<Reservation> olist = FXCollections.observableArrayList();

        try {
            if(customerValue != null && !customerValue.trim().equals("")) {
                try {
                    r.setCustomerName(customerValue);
//TODO error_customerID.setVisible(false);
                } catch (Exception e) {
//TODO error_customerID.setVisible(true);
                }
            }

//TODO find reservation method implemented??
            olist = ReservationServiceImpl.initialize().findCustomer(r);
            resbulka.setItems(olist);
        } catch (Exception e) {
            logger.info("exception refreshing table");
            e.printStackTrace();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Reservation r = new Reservation();
        ReservationService rs = ReservationServiceImpl.initialize();
        resbulka.setItems(rs.find(r));

        initializeResTable();

        tf_customer.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                customerValue = tf_customer.getText();
                refreshTable();
            }
        });
    }

    @FXML
    public void mouseClick(MouseEvent arg0) {
        resbulka.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        clickedRes = new HashSet<Reservation>(resbulka.getSelectionModel().getSelectedItems());

    }
}