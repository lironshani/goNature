package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class RegisterSubscriberAndGuideController implements Initializable {

	@FXML
	private Label expiraqtionDateLabel;

	@FXML
	private Label creditCardNumLabel;

	@FXML
	private Label separetorLabel;
	
    @FXML
    private Label csvCodeLabel;

	@FXML
	private TextField idTextFiled;

	@FXML
	private Spinner<Integer> familiyCount;

	@FXML
	private TextField fNameTextFiled;

	@FXML
	private TextField cardNumber;

	@FXML
	private CheckBox isCard;

	@FXML
	private Button registerBtn;

	@FXML
	private Button clearBtn;

	@FXML
	private TextField lNameTextFiled;

	@FXML
	private ComboBox<String> monthComboBox;

	@FXML
	private ComboBox<String> yearComboBox;

	@FXML
	private TextField emailTextField;

	@FXML
	private ComboBox<String> prefixPhoneComboBox;

	@FXML
	private TextField finishPhone;

	@FXML
	private TextField csvTextField;

	@FXML
	private CheckBox isGuide;

	@FXML
	void clearCreditCardDes(ActionEvent event) {

	}

	private ObservableList<String> phonePrefixObsList = FXCollections.observableArrayList("052", "050", "054");
	private ObservableList<String> yearObsList = FXCollections.observableArrayList("2021", "2022", "2023", "2024",
			"2025", "2026", "2027");
	private ObservableList<String> monthObsList = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06",
			"07", "08", "09", "10", "11", "12");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		yearComboBox.visibleProperty().bind(isCard.selectedProperty());
		monthComboBox.visibleProperty().bind(isCard.selectedProperty());
		csvTextField.visibleProperty().bind(isCard.selectedProperty());
		cardNumber.visibleProperty().bind(isCard.selectedProperty());
		creditCardNumLabel.visibleProperty().bind(isCard.selectedProperty());
		expiraqtionDateLabel.visibleProperty().bind(isCard.selectedProperty());
		separetorLabel.visibleProperty().bind(isCard.selectedProperty());
		csvCodeLabel.visibleProperty().bind(isCard.selectedProperty());
		prefixPhoneComboBox.setPrefWidth(100);
		prefixPhoneComboBox.setItems(phonePrefixObsList);
		prefixPhoneComboBox.getSelectionModel().selectFirst();
		yearComboBox.prefWidthProperty().bind(lNameTextFiled.widthProperty());
		yearComboBox.setItems(yearObsList);
		yearComboBox.getSelectionModel().selectFirst();
		monthComboBox.prefWidthProperty().bind(lNameTextFiled.widthProperty());
		monthComboBox.setItems(monthObsList);
		monthComboBox.getSelectionModel().selectFirst();
		familiyCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15));
	}

	@FXML
	void registerBtnClick(ActionEvent event) {
		if (!IsValidInputForRegistration()) {
			// show error
			return;
		}
		// SendInfoToServer();
	}

	/*
	 * method for input checks for the registration fields
	 */
	public boolean IsValidInputForRegistration() {
		// input checks for ID
		// check if ID contains only digits
		if (!idTextFiled.getText().matches("[0-9]+")) {
			GUIControl.popUpError("ID can only contain digits");
			return false;
		}
		// check if ID contains 9 digits
		if (idTextFiled.getText().length() != 9) {
			GUIControl.popUpError("ID need to contain 9 digits");
			return false;
		}

		// input check for Email
		// check if Email is in the correct format
		if (!emailTextField.getText().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
			GUIControl.popUpError("Email is not in the correct format");
			return false;
		}

		// input check for first name and last name
		// check if First Name contains only letters
		if (!fNameTextFiled.getText().matches("^[a-zA-Z]*$") || !lNameTextFiled.getText().matches("^[a-zA-Z]*$")) {
			GUIControl.popUpError("First name and Last name can only contain letters");
			return false;
		}

		// input checks for phone number
		// check if phone number contains only digits
		if (!finishPhone.getText().matches("[0-9]+")) {
			GUIControl.popUpError("phone number can only contain digits");
			return false;
		}
		// check if ID contains 9 digits
		if (finishPhone.getText().length() != 7) {
			GUIControl.popUpError("phone number need to contain 10 digits in total");
			return false;
		}

		// input checks for credit card number
		// check if credit card number contains only digits
		if (!cardNumber.getText().matches("[0-9]+")) {
			GUIControl.popUpError("credit card number can only contain digits");
			return false;
		}
		// check if ID contains 9 digits
		if (cardNumber.getText().length() != 16) {
			GUIControl.popUpError("credit card number need to contain 16 digits");
			return false;
		}

		// input checks for CSV code
		// check if CSV code contains only digits
		if (!csvTextField.getText().matches("[0-9]+")) {
			GUIControl.popUpError("CSV code can only contain digits");
			return false;
		}
		// check if CSV code contains 9 digits
		if (csvTextField.getText().length() != 3) {
			GUIControl.popUpError("CSV code need to contain 3 digits");
			return false;
		}

		return true;
	}

	@FXML
	void clearBtnClick(ActionEvent event) {
		idTextFiled.setText("");
		familiyCount.getValueFactory().setValue(0);
		fNameTextFiled.setText("");
		cardNumber.setText("");
		isCard.setSelected(false);
		lNameTextFiled.setText("");
		monthComboBox.getSelectionModel().selectFirst();
		yearComboBox.getSelectionModel().selectFirst();
		emailTextField.setText("");
		prefixPhoneComboBox.getSelectionModel().selectFirst();
		finishPhone.setText("");
		csvTextField.setText("");
		isGuide.setSelected(false);
	}

}
