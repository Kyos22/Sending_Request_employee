package application;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

import application.entities.Account;
import application.entities.UserSession;
import application.model.AccountModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomePage implements Initializable {

	@FXML
	private ImageView accountImageView;

	@FXML
	private Pane centerPane;
	
	@FXML
    private MenuButton profileMenu;
	

    @FXML
    private MenuItem buttonLogout;

    @FXML
    private MenuItem buttonProfile;
    
    @FXML
    private Label labelToday;

    
    

	public void updateAccountInfo() {
		String username = UserSession.getInstace().getUsername();
        if (username != null && !username.isEmpty()) {
            profileMenu.setText(username);
            
         // Lấy hình ảnh từ cơ sở dữ liệu
            AccountModel accountModel = new AccountModel();
            Account account = accountModel.findByUsername(username);

        if (account.getHinhanh() != null) {
        	try {
                Image image = new Image(new ByteArrayInputStream(account.getHinhanh()));
                accountImageView.setImage(image);
            } catch(Exception e) {
                e.printStackTrace();
                
            }
                
            }
        }
    }
	private void loadDefaultPane() {
	    try {
	        
	        Pane defaultView = FXMLLoader.load(getClass().getResource("Pane_history.fxml"));
	        centerPane.getChildren().setAll(defaultView);
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}
	@FXML
    void buttonProfile(ActionEvent event) {
try {
	        
	        AnchorPane ProfileView = FXMLLoader.load(getClass().getResource("ProfilePage.fxml"));
	        centerPane.getChildren().setAll(ProfileView);
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
    }

    @FXML
    void logout(ActionEvent event) {
    	UserSession.getInstace().endSession();

        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml")); 
            Parent root = loader.load();
            
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.setTitle("Login"); // bạn có thể đặt tiêu đề phù hợp
            loginStage.show();
            
            Stage currentStage = (Stage) accountImageView.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//phải gọi yourfunction trước những cái hàm khác
		yourFunction();
		updateAccountInfo();
		loadDefaultPane();
		
		
	}
	@FXML
    void history(ActionEvent event) {
		try {
	        
	        Pane defaultView = FXMLLoader.load(getClass().getResource("Pane_history.fxml"));
	        centerPane.getChildren().setAll(defaultView);
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
    }
	@FXML
    void buttonCrease(ActionEvent event) {
		try {
	        
	        Pane defaultView = FXMLLoader.load(getClass().getResource("RequestPage.fxml"));
	        centerPane.getChildren().setAll(defaultView);
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
    }
	@FXML
	void buttonSearch (ActionEvent event) {
		try {
			Pane defaultView = FXMLLoader.load(getClass().getResource("SearchPage.fxml"));
			centerPane.getChildren().setAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	void preview (ActionEvent event) {
		try {
			Pane defaultView = FXMLLoader.load(getClass().getResource("MediaPage.fxml"));
			centerPane.getChildren().setAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	void emailPage (ActionEvent event) {
		try {
			Pane defaultView = FXMLLoader.load(getClass().getResource("EmailPage.fxml"));
			centerPane.getChildren().setAll(defaultView);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//hàm lấy thời gian hiện tại
	public void yourFunction() {
        // Lấy ngày hiện tại
        LocalDate today = LocalDate.now();

        String dayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        String day = String.valueOf(today.getDayOfMonth());
        String month = today.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        String year = String.valueOf(today.getYear());

        String displayDate = dayOfWeek + "__" + day + "/" + month + "/" + year;

        labelToday.setText(displayDate);
        
       
    }

}
