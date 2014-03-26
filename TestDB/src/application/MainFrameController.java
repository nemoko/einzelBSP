package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import entity.Box;
import service.impl.BoxService;
import service.impl.BoxServiceImpl;

import java.sql.SQLException;

public class MainFrameController {

    private static final String db_URL = "jdbc:hsqldb:hsql://localhost:9001/xdb";
    private static final String db_USR = "SA";
    private static final String db_PWD = "";
	
	private static final Logger logger = Logger.getLogger(MainFrameController.class);
    private static final BoxService boxService = new BoxServiceImpl(db_URL,db_USR,db_PWD);

    @FXML
    private TextField tf_size;
    @FXML
    private TextField tf_strawFloor;
    @FXML
    private TextField tf_window;
    @FXML
    private TextField tf_outside;
    @FXML
    private TextField tf_picURL;
    @FXML
    private TextField tf_horseName;
    @FXML
    private TextField tf_deleted;
    @FXML
    private TextField tf_dailyRate;

/*
  WHAT DOES THIS METHOD DO?

	public void initialize(){
        boxService = new BoxServiceImpl();
	}
*/

	@FXML
	private void onSaveClicked() throws SQLException {
		logger.info("saveButtonClicked");

        Box b = new Box();

        b.setSize(Integer.parseInt(tf_size.getText()));
        b.setWindow(Boolean.parseBoolean(tf_window.getText()));
        b.setPicURL(tf_picURL.getText());
        b.setStrawFloor(Boolean.parseBoolean(tf_strawFloor.getText()));
        b.setOutside(Boolean.parseBoolean(tf_outside.getText()));
        b.setHorseName(tf_horseName.getText());
        b.setDailyRate(Integer.parseInt(tf_dailyRate.getText()));
        b.setDeleted(Boolean.parseBoolean(tf_deleted.getText()));

//        b.setSize(10);
//        b.setWindow(true);
//        b.setPicURL("url");
//        b.setStrawFloor(true);
//        b.setOutside(true);
//        b.setHorseName("Sherley");
//        b.setDailyRate(10);
//        b.setDeleted(false);

        boxService.create(b);
	}
	
}
