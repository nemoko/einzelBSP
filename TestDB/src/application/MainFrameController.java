package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import entity.Box;
import service.impl.BoxService;
import service.impl.BoxServiceImpl;

public class MainFrameController {
	
	private static final Logger logger = Logger.getLogger(MainFrameController.class);
	
    private BoxService boxService;

    @FXML
    private TextField tf_size;
    @FXML
    private TextField tf_strawFloor;
//    @FXML
//    private TextField tf_window;
//    @FXML
//    private TextField tf_outside;
    @FXML
    private TextField tf_picURL;
	
	public void initialize(){
        boxService = new BoxServiceImpl();
	}
	
	@FXML
	private void onSpeichernClicked(){
		logger.info("saveButtonClicked");

        Box b = new Box();
        b.setSize(Integer.parseInt(tf_size.getText()));
        b.setStrawFloor(Boolean.parseBoolean(tf_strawFloor.getText()));
//        b.isWindow(Boolean.parseBoolean(tf_window.getText()));
//        b.isOutside(Boolean.parseBoolean(tf_outside.getText()));
        b.setPicURL(tf_picURL.getText());

        boxService.create(b);
	}
	
}
