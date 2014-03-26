package application;

import entity.Box;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

    private static final Logger logger = Logger.getLogger(MainFrameController.class);


    @FXML
    private Box clicked;
    @FXML
    private TableView<Box> tabulka;
    @FXML
    private Button reserveButton;
    @FXML
    private Button unReserveButton;


    @FXML
    public void onActionHomePage() {
        MainController.setWindow("Hybrid.fxml");
    }

    @FXML
    public void onActionCreateReservation() {
        logger.info("onReserveBoxClicked");

        MainController.boxService.update(clicked);
    }

    @FXML
    public void onActionCancelReservation() {

    }

    public void initializeTable() {
        TableColumn<Box, Integer> id = new TableColumn<Box,Integer>("ID");
        id.setMinWidth(60);
        id.setCellValueFactory(new PropertyValueFactory<Box, Integer>("id"));

        TableColumn<Box, Integer> dailyrate = new TableColumn<Box,Integer>("dailyrate");
        dailyrate.setMinWidth(40);
        dailyrate.setCellValueFactory(new PropertyValueFactory<Box, Integer>("dailyrate"));

        TableColumn<Box, Integer> picURL = new TableColumn<Box,Integer>("pic");
        picURL.setMinWidth(40);
        picURL.setCellValueFactory(new PropertyValueFactory<Box, Integer>("picURL"));

        TableColumn<Box, Integer> size = new TableColumn<Box,Integer>("size");
        size.setMinWidth(40);
        size.setCellValueFactory(new PropertyValueFactory<Box, Integer>("size"));

        TableColumn<Box, Integer> floor = new TableColumn<Box,Integer>("floor");
        floor.setMinWidth(40);
        floor.setCellValueFactory(new PropertyValueFactory<Box, Integer>("floor"));

        TableColumn<Box, Integer> window = new TableColumn<Box,Integer>("window");
        window.setMinWidth(40);
        window.setCellValueFactory(new PropertyValueFactory<Box, Integer>("window"));

        TableColumn<Box, Integer> outside = new TableColumn<Box,Integer>("outside");
        outside.setMinWidth(40);
        outside.setCellValueFactory(new PropertyValueFactory<Box, Integer>("outside"));

        tabulka.getColumns().addAll(id,dailyrate,picURL,size,floor,window,outside);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabulka.setItems(MainController.boxService.find());

        initializeTable();
    }

    @FXML
    public void mouseClick(MouseEvent arg0) {
        clicked = tabulka.getSelectionModel().getSelectedItem();
        reserveButton.setDisable(false);
        unReserveButton.setDisable(false);
    }
}
