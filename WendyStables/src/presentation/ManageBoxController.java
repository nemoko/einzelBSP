package presentation;

import entity.Box;
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
    private ComboBox<String> cb_floor;
    private String floor_type;
    @FXML
    private Label e_daily;
    @FXML
    private Label e_size;

    @FXML
    public void onActionHomePage() {
        MainController.setWindow("Welcome.fxml");
    }

    @FXML
    public void onActionCreateBox() {
        logger.info("OnActionCreateBox clicked");

        Box b = new Box();

        try {
            b.setDailyRate(Integer.parseInt(tf_daily_rate.getText()));
            e_daily.setVisible(false);
        } catch (Exception e) {
            e_daily.setVisible(true);
        }

        try {
            b.setSize(Integer.parseInt(tf_size.getText()));
            e_size.setVisible(false);
        } catch (Exception e) {
            e_size.setVisible(true);
        }

        b.setPicURL("");
        b.setFloor(floor_type);
        b.setWindow(ch_window.isSelected());
        b.setOutside(ch_outside.isSelected());

        BoxService bs = BoxServiceImpl.initialize();
        bs.create(b);
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
            ch_window.setSelected(clicked.isWindow());
            ch_outside.setSelected(clicked.isOutside());
            tf_size.setText("" + clicked.getSize());

        }
        b_filter.setDisable(false);
        b_create.setDisable(false);
        b_update.setDisable(false);
        b_delete.setDisable(false);
    }
}
