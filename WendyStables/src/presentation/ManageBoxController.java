package presentation;

import entity.Box;
import exception.BoxException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;
import service.BoxService;
import service.BoxServiceImpl;

import java.net.URL;
import java.util.ResourceBundle;


public class ManageBoxController implements Initializable {

    private static final Logger logger = Logger.getLogger(ManageBoxController.class);

    @FXML
    private Box clicked;
    @FXML
    private TableView<Box> tabulka;
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
    private Button b_create;
    @FXML
    private Button b_update;
    @FXML
    private Button b_delete;
    @FXML
    private Button b_clearall;
    @FXML
    private ComboBox<String> cb_floor;
    private String floor_type;
    @FXML
    private Label e_daily_int;
    @FXML
    private Label e_size;
    @FXML
    private Label e_floor;
    @FXML
    private Label e_window;
    @FXML
    private Label e_outside;
    @FXML
    private Label e_failed;
    @FXML
    private Label box_created;
    @FXML
    private Label box_updated;
    @FXML
    private Label box_deleted;
    @FXML
    private Label e_daily_positive;
    @FXML
    private Label e_size_positive;
    @FXML
    private Label e_filter_failed;
    @FXML
    private Label filter_applied;
    @FXML
    private Label e_filter_criteria;


    @FXML
    public void removeSuccessMessage() {
        box_created.setVisible(false);
        box_updated.setVisible(false);
        box_deleted.setVisible(false);
        e_failed.setVisible(false);
        e_filter_criteria.setVisible(false);
        filter_applied.setVisible(false);
        e_filter_failed.setVisible(false);
    }

    public void clearErrors() {
        box_created.setVisible(false);
        box_updated.setVisible(false);
        box_deleted.setVisible(false);
        e_failed.setVisible(false);
        e_filter_failed.setVisible(false);
        filter_applied.setVisible(false);
        e_filter_criteria.setVisible(false);
    }

    @FXML
    public void onActionHomePage() {
        MainController.setWindow("Welcome.fxml");
    }



    @FXML
    public void onActionCreateBox() {
        clearErrors();

        logger.info("OnActionCreateBox clicked");

        Box b = new Box();
        String exception = "";

        try {
            e_daily_positive.setVisible(false);
            e_daily_int.setVisible(false);
            b.setDailyRate(Integer.parseInt(tf_daily_rate.getText()));
            if(Integer.signum(Integer.parseInt(tf_daily_rate.getText())) == -1) e_daily_positive.setVisible(true);
        } catch (Exception e) {
            exception += "0";
        }

        try {
            e_size_positive.setVisible(false);
            e_size.setVisible(false);
            b.setSize(Integer.parseInt(tf_size.getText()));
            if(Integer.signum(Integer.parseInt(tf_size.getText())) == -1) e_size_positive.setVisible(true);
        } catch (Exception e) {
            exception += "1";
        }

        e_floor.setVisible(false);

        if(floor_type == null || floor_type.isEmpty()) exception += "2";
        else b.setFloor(floor_type);

        try {
            e_window.setVisible(false);
            b.setWindow(ch_window.isSelected());
        } catch (Exception e) {
            exception += "3";
        }

        try {
            e_outside.setVisible(false);
            b.setOutside(ch_outside.isSelected());
        } catch (Exception e) {
            exception += "4";
        }

        b.setPicURL("");

        if(exception.contains("0")) e_daily_int.setVisible(true);
        if(exception.contains("1")) e_size.setVisible(true);
        if(exception.contains("2")) e_floor.setVisible(true);
        if(exception.contains("3")) e_window.setVisible(true);
        if(exception.contains("4")) e_outside.setVisible(true);

        if(!exception.isEmpty() || e_daily_positive.isVisible() || e_size_positive.isVisible()) return;

        try {
            BoxService bs = BoxServiceImpl.initialize();
            bs.create(b);
            box_created.setVisible(true);
        } catch (BoxException be) {
            e_failed.setVisible(true);
        }
    }

    @FXML
    public void onActionClearAll() {
        tf_daily_rate.setText("");
        tf_size.setText("");
        cb_floor.setValue("");
        ch_window.setSelected(false);
        ch_window.setDisable(false);
        ch_outside.setSelected(false);
        ch_outside.setDisable(false);
        e_daily_int.setVisible(false);
        e_size.setVisible(false);
        e_floor.setVisible(false);
        e_window.setVisible(false);
        e_outside.setVisible(false);
        e_daily_positive.setVisible(false);
        e_size_positive.setVisible(false);
        b_update.setDisable(true);
        b_delete.setDisable(true);
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
        clearErrors();

        Box b = new Box();
        String exception = "";

        ObservableList<Box> olist = null;

        //remove error messages from gui
        e_daily_positive.setVisible(false);
        e_daily_int.setVisible(false);
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

        //remove error messages from gui
        e_size_positive.setVisible(false);
        e_size.setVisible(false);
        if(!tf_size.getText().isEmpty()) { //if field is not empty
            try {
                if(Integer.signum(Integer.parseInt(tf_size.getText())) == -1) {
                    e_size_positive.setVisible(true); //if int is negative, show error
                    exception += "1";
                } else {
                    b.setSize(Integer.parseInt(tf_size.getText())); //add size to find criteria
                }
            } catch (NumberFormatException ne) { //if string entered instead of int, show error
                e_size.setVisible(true);
                exception += "1";
            }
        } else exception += "1"; //if EMPTY

//      e_floor.setVisible(false);
        if(floor_type == null || floor_type.isEmpty()) exception += "2"; //if no floor type, add to exception string -> decide if any criteria is selected
        else b.setFloor(floor_type);

        //if neither window or outside is selected
        if(!ch_window.isSelected()) exception += "3";
        if(!ch_outside.isSelected()) exception += "4";

        b.setPicURL("");

        if(exception.contains("01234")) {
            e_filter_criteria.setVisible(true);
            return;
        }

        //if neither window or outside is selected, prompt for selection
        if(exception.contains("3") && exception.contains("4")) {
            e_window.setVisible(true);
            e_outside.setVisible(true);
            return;
        } else {
            b.setWindow(ch_window.isSelected());
            b.setOutside(ch_outside.isSelected());
        }

        try {
            olist = BoxServiceImpl.initialize().find(b);
            tabulka.setItems(olist);
            filter_applied.setVisible(true);
        } catch (Exception e) {
            logger.info("Exception refreshing table");
            e.printStackTrace();
            e_filter_failed.setVisible(true);
        }



/*
//        if(clicked == null) {
//            e_filter_criteria.setVisible(true);
//            return;
//        }
//        Box b = new Box();


        if(!exception.isEmpty() || e_daily_positive.isVisible() || e_size_positive.isVisible()) return;

*/
    }

    @FXML
    public void onActionDisplayAll() {
        clearErrors();

        Box b = new Box();
        ObservableList<Box> olist = null;

        try {
            olist = BoxServiceImpl.initialize().find(b);
            tabulka.setItems(olist);
            filter_applied.setVisible(true);
        } catch (Exception e) {
            logger.info("Exception refreshing table");
            e.printStackTrace();
            e_filter_failed.setVisible(true);
        }

    }

    @FXML
    public void onActionUpdateBox() {
        clearErrors();

    }

    @FXML
    public void onActionDeleteBox() {
        clearErrors();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        b_filter.setDisable(false);
        b_create.setDisable(false);
        b_update.setDisable(true);
        b_delete.setDisable(true);

        cb_floor.getItems().addAll("Sawdust","Straw");
        cb_floor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (cb_floor.getValue() != null) floor_type = cb_floor.getValue();
            }
        });

        Box b = new Box();

        BoxService bx = BoxServiceImpl.initialize();

        ObservableList<Box> ob = bx.find(b);
        tabulka.setItems(ob);

        initializeTable();
    }

    public void initializeTable() {
//        TableColumn<Box, Integer> id = new TableColumn<Box,Integer>("ID");
//        id.setMinWidth(60);
//        id.setCellValueFactory(new PropertyValueFactory<Box, Integer>("id"));

        TableColumn<Box, Integer> dailyrate = new TableColumn<Box,Integer>("Daily Rate");
        dailyrate.setMinWidth(90);
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

        tabulka.getColumns().addAll(picURL,dailyrate,size,floor,window,outside);
    }

    @FXML
    public void mouseClick(MouseEvent arg0) {
        clicked = tabulka.getSelectionModel().getSelectedItem();

        if(clicked != null) {
            tf_daily_rate.setText("" + clicked.getDailyRate());
            cb_floor.setValue(clicked.getFloor());
            if(clicked.isWindow()) {
                ch_window.setSelected(true);
                ch_window.setDisable(false);
                ch_outside.setSelected(false);
                ch_outside.setDisable(true);
            }
            if(clicked.isOutside()) {
                ch_outside.setSelected(true);
                ch_outside.setDisable(false);
                ch_window.setSelected(false);
                ch_window.setDisable(true);
            }
            tf_size.setText("" + clicked.getSize());

        }
        b_filter.setDisable(false);
        b_create.setDisable(false);
        b_update.setDisable(false);
        b_delete.setDisable(false);
    }
}
