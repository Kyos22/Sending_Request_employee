package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import application.entities.Account;
import application.entities.Madouutien;
import application.entities.Request;
import application.entities.UserSession;
import application.model.AccountModel;
import application.model.MadouutienModel;
import application.model.RequestModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class RequestController implements Initializable {

    @FXML
    private Button buttonsend;

    @FXML
    private ComboBox<Madouutien> comboboxmadouutien;

 

    @FXML
    private DatePicker inputngaygui;

    @FXML
    private TextArea inputnoidung;

    @FXML
    private TextField inputtieude;

    @FXML
    void buttonSendRequest(ActionEvent event) {
    	Request request = new Request();
    	request.setTieude(inputtieude.getText());
    	request.setNoidung(inputnoidung.getText());
    	
    	LocalDate created = inputngaygui.getValue();
    	request.setNgaygui(created);
    	
    	int value = comboboxmadouutien.getValue().getId();
    	request.setMadouutien(value);
    	
    	int value_userId = UserSession.getInstace().getUserId();
    	request.setManv_gui(value_userId);
    	
    	if(RequestModel.create(request)) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("success");
    		alert.show();
    	}else {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("failed");
    		alert.show();
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadMadouutien();
		
		
		formatDatePicker(inputngaygui);
	    
		
	}
	private void loadMadouutien() {
        MadouutienModel model = new MadouutienModel();
        List<Madouutien> list = model.getAllMadouutien();        
        comboboxmadouutien.getItems().addAll(list);
        }
	
	
    
	
	private void formatDatePicker(DatePicker datePicker) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    datePicker.setConverter(new javafx.util.StringConverter<java.time.LocalDate>() {
	        @Override
	        public java.time.LocalDate fromString(String string) {
	            if (string != null && !string.isEmpty()) {
	                return java.time.LocalDate.parse(string, formatter);
	            } else {
	                return null;
	            }
	        }

	        @Override
	        public String toString(java.time.LocalDate date) {
	            if (date != null) {
	                return formatter.format(date);
	            } else {
	                return "";
	            }
	        }
	    });
	}
}









