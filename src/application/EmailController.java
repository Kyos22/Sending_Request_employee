package application;

import application.helper.MailHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EmailController {

    @FXML
    private Button buttonsend;

    @FXML
    private TextField jTextFieldSubject;

    @FXML
    private TextField jTextFieldTo;

    @FXML 
    private TextArea jtextAreaContent;

    @FXML
    private TextField jtextfieldfrom;

    @FXML
    void send(ActionEvent event) {
    	String from = jtextfieldfrom.getText().trim();
    	String to = jTextFieldTo.getText().trim();
		String subject = jTextFieldSubject.getText().trim();
		String content = jtextAreaContent.getText();
		if(MailHelper.send(from, to, subject, content)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("email was sent");
			alert.show();
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Sending failed");
			alert.show();
		}
    }

}
