package gui;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import message.ClientMessage;
import message.ClientMessageType;

public class ParkManagerReportsController {

	Stage primaryStage;
	@FXML
	private AnchorPane anchorPane;

	@FXML
	void capacityReport(ActionEvent event) {
		Parent root ;
        try {
        	 root = FXMLLoader.load(getClass().getResource(ClientConstants.Screens.PARK_CAPACITY_REPORT.toString()));
            Stage stage = new Stage();
            stage.setTitle("park manager capacit report");
           stage.setScene(new Scene(root,964, 653));
            stage.show();
	} catch(Exception e) {
		e.printStackTrace();
	}
	}

	@FXML
	void incomeReport(ActionEvent event) {
		Parent root ;
        try {
        	 root = FXMLLoader.load(getClass().getResource(ClientConstants.Screens.PARK_INCOME_REPORT.toString()));
            Stage stage = new Stage();
            stage.setTitle("park manager icome report");
            stage.setScene(new Scene(root,696, 677));
            stage.show();
	} catch(Exception e) {
		e.printStackTrace();
	}

	}

	@FXML
	
	void visitationReport(ActionEvent event) {
		Parent root ;
        try {
        	 root = FXMLLoader.load(getClass().getResource(ClientConstants.Screens.PARK_MNG_VISIT_REPORT.toString()));
            Stage stage = new Stage();
            stage.setTitle("park manager visitation report");
            stage.setScene(new Scene(root,1027, 719));
            stage.show();
	} catch(Exception e) {
		e.printStackTrace();
	}
        
	
	}
}
