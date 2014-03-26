package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entity.*;
import service.impl.*;

public class MainFrameController implements Initializable {

    private static final String db_URL = "jdbc:hsqldb:hsql://localhost:9001/xdb";
    private static final String db_USR = "SA";
    private static final String db_PWD = "";
	
	private static final Logger logger = Logger.getLogger(MainFrameController.class);

    private static final BoxService boxService = new BoxServiceImpl(db_URL,db_USR,db_PWD);
    private static final ReservationService reservationService = new ReservationServiceImpl(db_URL,db_USR,db_PWD);
    private static final ReceiptService receiptService = new ReceiptServiceImpl(db_URL,db_USR,db_PWD);

    @FXML
    private Box clicked;
    @FXML
    private ListView<Box> list;
    @FXML
    private TextField tf_box_size;
    @FXML
    private CheckBox cb_box_window;
    @FXML
    private CheckBox cb_box_outside;
    @FXML
    private TextField tf_box_picURL;
    @FXML
    private TextField tf_box_dailyRate;
    @FXML
    private TextField tf_box_delete;
    @FXML
    private TextField tf_box_floor;
    @FXML
    private TextField tf_box_ID;
    @FXML
    private TextField tf_box_reservationID;
    @FXML
    private TextField tf_rec_dailyRate;
    @FXML
    private TextField tf_rec_totalCharge;
    @FXML
    private TextField tf_res_customerName;
    @FXML
    private TextField tf_res_horseName;
    @FXML
    private TextField tf_res_start;
    @FXML
    private TextField tf_res_end;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list.setItems(boxService.find());
    }

    @FXML
	private void onSaveBoxClicked() throws SQLException {
		logger.info("saveBoxButtonClicked");

        Box b = new Box();

        b.setSize(Integer.parseInt(tf_box_size.getText()));
        b.setWindow(cb_box_window.isSelected());
        b.setOutside(cb_box_outside.isSelected());
        if(tf_box_picURL.getText() != "") b.setPicURL(tf_box_picURL.getText());
        b.setFloor("");
        b.setDailyRate(Integer.parseInt(tf_box_dailyRate.getText()));

        boxService.create(b);
	}

    @FXML
    private void onReserveBoxClicked() throws SQLException {
        logger.info("onReserveBoxClicked");

        Box b = new Box();

        b.setId(Integer.parseInt(tf_box_ID.getText()));
        b.setReservationID(Integer.parseInt(tf_box_reservationID.getText()));

        boxService.update(b);
    }

    @FXML
    private void onDeleteBoxClicked() throws SQLException {
        logger.info("deleteBoxClicked");

        Box b = new Box();

        b.setId(Integer.parseInt(tf_box_delete.getText()));

        boxService.delete(b);
    }

    @FXML
    private void onSaveReservationClicked() throws SQLException {
        logger.info("saveReservationButtonClicked");

        Reservation r = new Reservation();

        r.setCustomerName(tf_res_customerName.getText());
        r.setHorseName(tf_res_horseName.getText());
        r.setStart(Date.valueOf(tf_res_start.getText()));
        r.setEnd(Date.valueOf(tf_res_end.getText()));

        reservationService.create(r);
    }


    @FXML
    private void onSaveReceiptClicked() throws SQLException {
        logger.info("saveReceiptButtonClicked");

        Receipt r = new Receipt();

        r.setDailyRate(Integer.parseInt(tf_rec_dailyRate.getText()));
        r.setTotalCharge(Integer.parseInt(tf_rec_totalCharge.getText()));

        receiptService.create(r);
    }

    @FXML
    public void mouseClick(MouseEvent arg0) {
        clicked = list.getSelectionModel().getSelectedItem();
    }
}