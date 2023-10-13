package application;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import application.entities.Account;
import application.entities.UserSession;
import application.model.AccountModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class ProfileController implements Initializable {

    @FXML
    private TextField InputUsernameProfile;

    @FXML
    private Button buttonBrowse;

    @FXML
    private Button buttonSave;

    @FXML
    private ImageView imageViewProfile;

    @FXML
    private DatePicker inputBirthdayProfile;

    @FXML
    private TextField inputFullNameProfile;

    @FXML
    private PasswordField inputPasswordProfile;

    @FXML
    private Label myLabel;
    
    private File selectedImageFile;
    
    
    
    @FXML
    void buttonBrowse(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
        // ... (các thiết lập khác cho fileChooser)
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Cập nhật ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imageViewProfile.setImage(image);
            // Lưu file hình ảnh đã chọn
            selectedImageFile = selectedFile;
        } else {
            System.out.println("Image selection cancelled.");
        }
    }
    @FXML
    void savebutton(ActionEvent event) {
        try {
            AccountModel accountModel = new AccountModel();

            // Lấy thông tin từ giao diện và cập nhật vào đối tượng currentUser
            String currentUsername = UserSession.getInstace().getUsername();
            Account currentUser = accountModel.findByUsername(currentUsername);

            if (currentUser != null) {
                currentUser.setUsername(InputUsernameProfile.getText());
                currentUser.setHoten(inputFullNameProfile.getText());
                
                currentUser.setPassword(inputPasswordProfile.getText());
                
                currentUser.setNgaysinh(inputBirthdayProfile.getValue());

                // Cập nhật hình ảnh nếu người dùng chọn một tệp mới
                if (selectedImageFile != null) {
                    try {
                        byte[] imageData = Files.readAllBytes(selectedImageFile.toPath());
                        currentUser.setHinhanh(imageData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // Cập nhật thông tin người dùng vào cơ sở dữ liệu
                if (accountModel.updateWithoutPassword(currentUser)) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Profile updated successfully!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to update profile. Please try again.");
                    alert.showAndWait();
                }
            }
        } catch (Exception ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred: " + ex.getMessage());
            alert.showAndWait();
        }
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		inputPasswordProfile.setEditable(false);
		loadUserData();
		formatDatePicker(inputBirthdayProfile);
	    
	}
	private void loadUserData() {
	    // 1. Lấy thông tin người dùng đang đăng nhập
	    String currentUsername = UserSession.getInstace().getUsername();

	    // 2. Tải dữ liệu từ cơ sở dữ liệu
	    AccountModel accountModel = new AccountModel();
	    Account currentUser = accountModel.findByUsername(currentUsername);
	    
	    if (currentUser != null) {
	        // 3. Hiển thị dữ liệu lên giao diện
	        
	        // Đặt tên người dùng và mật khẩu (đã giải mã)
	        InputUsernameProfile.setText(currentUser.getUsername());
	        inputPasswordProfile.setText(""); // Bạn không nên hiển thị mật khẩu thực sự dù đã giải mã
	        inputFullNameProfile.setText(currentUser.getHoten());
	        inputBirthdayProfile.setValue(currentUser.getNgaysinh());
	        
	        myLabel.setText(currentUser.getUsername());

	        // Đặt hình ảnh
	        byte[] imageBytes = currentUser.getHinhanh();
	        if (imageBytes != null) {
	            Image image = new Image(new ByteArrayInputStream(imageBytes));
	            imageViewProfile.setImage(image);
	        }
	    }
	    

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
