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
    private Label e_daily;
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
    public void removeSuccessMessage() {
        box_created.setVisible(false);
        box_updated.setVisible(false);
        box_deleted.setVisible(false);
    }

    @FXML
    public void onActionHomePage() {
        MainController.setWindow("Welcome.fxml");
    }

    @FXML
    public void onActionCreateBox() {
        logger.info("OnActionCreateBox clicked");

        Box b = new Box();
        String exception = "";

        try {
            b.setDailyRate(Integer.parseInt(tf_daily_rate.getText()));
            e_daily.setVisible(false);
            e_daily_positive.setVisible(false);
            if(Integer.signum(Integer.parseInt(tf_daily_rate.getText())) == -1) e_daily_positive.setVisible(true);
        } catch (Exception e) {
            exception += "0";
        }

        try {
            b.setSize(Integer.parseInt(tf_size.getText()));
            e_size.setVisible(false);
            e_size_positive.setVisible(false);
            if(Integer.signum(Integer.parseInt(tf_size.getText())) == -1) e_size_positive.setVisible(true);
        } catch (Exception e) {
            exception += "1";
        }

        e_floor.setVisible(false);

        if(floor_type == null) exception += "2";
        else b.setFloor(floor_type);

        try {
            b.setWindow(ch_window.isSelected());
            e_window.setVisible(false);
        } catch (Exception e) {
            exception += "3";
        }

        try {
            b.setOutside(ch_outside.isSelected());
            e_outside.setVisible(false);
        } catch (Exception e) {
            exception += "4";
        }

        b.setPicURL("");

        if(exception.contains("0")) e_daily.setVisible(true);
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
        Box b = new Box();

        ObservableList<Box> olist = null;

        try {
//            if(filter_id_value != null && !filter_id_value.trim().equals("")) {
//                try {
//                    b.setId(Integer.parseInt(filter_id_value));
//                    error_filter_id.setVisible(false);
//                } catch (Exception e) {
//                    error_filter_id.setVisible(true);
//                }
//            }

            olist = BoxServiceImpl.initialize().find(b);
            tabulka.setItems(olist);
        } catch (Exception e) {
            logger.info("Exception refreshing table");
            e.printStackTrace();
        }
    }

    @FXML
    public void onActionUpdateBox() {

    }

    @FXML
    public void onActionDeleteBox() {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        b_filter.setDisable(true);
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

        tabulka.setItems(bx.find(b));

        initializeTable();
    }

    public void initializeTable() {
        TableColumn<Box, Integer> id = new TableColumn<Box,Integer>("ID");
        id.setMinWidth(60);
        id.setCellValueFactory(new PropertyValueFactory<Box, Integer>("id"));

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

        tabulka.getColumns().addAll(id,picURL,dailyrate,size,floor,window,outside);
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
