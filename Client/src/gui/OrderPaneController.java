package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import entity.EntityConstants;
import entity.Park;
import entity.Subscriber;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import message.ClientMessage;
import message.ClientMessageType;

public class OrderPaneController implements Initializable {
	GUIControl guiControl=GUIControl.getInstance();
    @FXML
    private Label orderTripLabel;

    @FXML
    private ComboBox<String> parkNameComboBox;

    @FXML
    private Spinner<Integer> peopleAmount;

    @FXML
    private GridPane mailGrid;

    @FXML
    private TextField emailText;

    @FXML
    private DatePicker date;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private CheckBox guideGroupCheckBox;
    
    @FXML
    private GridPane guideGroupGridPane;

    @FXML
    private Button orderButton;

	private ObservableList<String> parkNameObservableList = FXCollections.observableArrayList();
    @FXML
    void changePeopleAmount(ActionEvent event) {
		int tmp = peopleAmount.getValue();
		if (guideGroupCheckBox.isSelected()) {
			peopleAmount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, ClientConstants.MAX_PEOPLE));
			if (tmp != 1)
				peopleAmount.getValueFactory().setValue(tmp);
		} else {
			peopleAmount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, ClientConstants.MAX_PEOPLE));
			peopleAmount.getValueFactory().setValue(tmp);
		}
    }

    @FXML
    void orderFunc(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(!(guiControl.getUser() instanceof Subscriber) || !((Subscriber)guiControl.getUser()).getIsGuide()) {
			guideGroupGridPane.setVisible(false);
		}	
		parkNameComboBox.prefWidthProperty().bind(emailText.widthProperty());
		peopleAmount.prefWidthProperty().bind(timeComboBox.widthProperty());
		date.prefWidthProperty().bind(emailText.widthProperty());
		timeComboBox.prefWidthProperty().bind(emailText.widthProperty());
		peopleAmount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, ClientConstants.MAX_PEOPLE));
		guiControl.sendToServer(new ClientMessage(ClientMessageType.GET_PARKS,null));
		List<Park> parkArr=(List<Park>)guiControl.getServerMsg().getMessage();
		for(Park p:parkArr)
			parkNameObservableList.add(p.getParkName());
		parkNameComboBox.setItems(parkNameObservableList);
		DatePicker minDate = new DatePicker();
		Date today = new Date();
		LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		minDate.setValue(LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth()));
		final Callback<DatePicker, DateCell> dayCellFactory;
		dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				if (item.isBefore(minDate.getValue())) {
					setDisable(true);
					setStyle("-fx-background-color: #ffc0cb;");
				}
			}
		};
		date.setDayCellFactory(dayCellFactory);
		date.setValue(localDate);
		for (int i = EntityConstants.PARK_OPEN; i <= EntityConstants.PARK_CLOSED; i++) {
			if (i < 10)
				timeComboBox.getItems().add("0" + i + ":00");
			else
				timeComboBox.getItems().add(i + ":00");
		}
		timeComboBox.getSelectionModel().selectFirst();
		parkNameComboBox.getSelectionModel().selectFirst();
		orderButton.disableProperty().bind(Bindings.createBooleanBinding(() -> {
			return !GUIControl.isEmailValid(emailText.getText());
		}, emailText.textProperty()));
	}

}
