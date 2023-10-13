package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;

import org.mindrot.jbcrypt.BCrypt;

import application.entities.Account;
import application.entities.UserSession;
import application.helper.MailHelper;
import application.model.AccountModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SampleController implements Initializable {

	@FXML
	private Label labelAlert;
	
    @FXML
    private Button button_login;

    @FXML
    private Button button_register;

    @FXML
    private Button buttonbrowse;

    @FXML
    private ImageView imageViewregister;

    @FXML
    private PasswordField inputpassword_login;

    @FXML
    private DatePicker inputregister_dayofbirth;

    @FXML
    private VBox inputregiterdayofbirth;

    @FXML
    private TextField inputregiterfullname;

    @FXML
    private PasswordField inputregiterpassword;

    @FXML
    private TextField inputregiterusername;

    @FXML
    private TextField inputusername_login;
    
    @FXML
    private TextField inputcode;
    
    @FXML
    private Label labelVerify;
    
    private String verificationCode;
    
    private File selectedImageFile;

    @FXML
    void login(ActionEvent event) {
    	String inputUsername = inputusername_login.getText();
        String inputPassword = inputpassword_login.getText();
       
        
        System.out.println(inputUsername);
        System.out.println(inputPassword);
        

        if (inputPassword.length() < 6) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Mật khẩu phải có ít nhất 6 ký tự!");
            alert.show();
            return;
        }
        
        AccountModel accountModel = new AccountModel();
        Account account = accountModel.findByUsername(inputUsername);

        if (account != null && BCrypt.checkpw(inputPassword, account.getPassword())) {
        
        	UserSession.getInstace(account.getUsername(), account.getId());
     	
        //vậy thì username không được phép trùng nhau
        	openHomePage();
        
//        if(account != null ) {
//        	 System.out.println("Account username from DB: " + account.getUsername());
//        	    System.out.println("Account hashed password from DB: " + account.getPassword());
//        	    System.out.println("Account verification code from DB: " + account.getVerificationCode());
//        
        	
        } else {
            // Đăng nhập thất bại
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Tên đăng nhập hoặc mật khẩu không đúng!");
            alert.show();
        }
    }
    
    

    @FXML
    void buttonVerify(ActionEvent event) {
    	try {
    		verificationCode = generateVerificationCode();
        	
        	String emailContent = "ma xac thuc cua ban la: " + verificationCode;
        	
        	boolean emailSent = MailHelper.send("nguyenthanhcongvt234@gmail.com", inputregiterfullname.getText(), "Mã xác thức", emailContent);

        	if (!emailSent) {
        		labelVerify.setText("Error");
        		return;
            }else {
            	labelVerify.setText("Success");
            	Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Sucess");
                alert.setContentText("Vao email de lay ma code xac thuc");
                alert.show();
                
                
                System.out.println(verificationCode);
            }
		} catch (Exception e) {
			e.printStackTrace();
			labelVerify.setText("Error");
			Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("email khong hop le");
            alert.show();
		}
    }
    
    @FXML
    void register(ActionEvent event) {
    	
    	String maCode = inputcode.getText();
    	
    	
    	Account account = new Account();
    	account.setUsername(inputregiterusername.getText());
    	
    	String password = new String(inputregiterpassword.getText());
    	
    	if (password.length() < 6) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Mật khẩu phải có ít nhất 6 ký tự!");
            alert.show();
            return; // Dừng lại ở đây và không thực hiện bất kỳ thao tác nào tiếp theo
        }
    	
    	account.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
    	account.setHoten(inputregiterfullname.getText());
    	
    	LocalDate birthDate = inputregister_dayofbirth.getValue();
    	account.setNgaysinh(birthDate);
    	
    	
    	 if (selectedImageFile != null) {
    	        try {
    	            byte[] imageBytes = Files.readAllBytes(selectedImageFile.toPath());
    	            account.setHinhanh(imageBytes);;
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }
    	    }
    	 account.setVerificationCode(verificationCode);
    	
    	if(AccountModel.create(account) && maCode.equals(account.getVerificationCode())) {
    		
    		System.out.println(maCode);
    		
    		
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("success");
    		alert.setContentText("ma code da duoc gui vao email cua ban, dung no de dang nhap");
    		alert.show();
    	}else {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("failed");
    		alert.show();
    	}
    }
    @FXML
    void buttonBrowse(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
        // ... (các thiết lập khác cho fileChooser)
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Cập nhật ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imageViewregister.setImage(image);
            // Lưu file hình ảnh đã chọn
            selectedImageFile = selectedFile;
        } else {
            System.out.println("Image selection cancelled.");
        }
    }
    private void openHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent root = loader.load();
            
            // Tạo một cửa sổ mới 
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Kyos Hello everyone");
            stage.show();
            stage.setResizable(false);
            
            // Đóng cửa sổ đăng nhập hiện tại 
            Stage currentStage = (Stage) button_login.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		formatDatePicker(inputregister_dayofbirth);
	    
		
	}
	//hàm randow mã code
	public String generateVerificationCode() {
	    Random random = new Random();
	    return String.format("%04d", random.nextInt(10000)); // tạo mã 4 chữ số
	}


}
