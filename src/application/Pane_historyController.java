package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import application.entities.Account;
import application.entities.Request;
import application.entities.UserSession;
import application.model.AccountModel;
import application.model.RequestModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class Pane_historyController implements Initializable {

    @FXML
    private Pane PaneRequest;
    
    @FXML
    private TableView<Request> paneRequestTable;


    @FXML
    private TableColumn<Request, Integer> colDouutien = new TableColumn<>();

    @FXML
    private TableColumn<Request, Integer> colId = new TableColumn<>();

    @FXML
    private TableColumn<Request, Integer> colId_nvgui = new TableColumn<>();

    @FXML
    private TableColumn<Request, Integer> colId_nvxuly = new TableColumn<>();

    @FXML
    private TableColumn<Request, LocalDate> colNgaygui = new TableColumn<>();

    @FXML
    private TableColumn<Request, String> colNoidung = new TableColumn<>();

    @FXML
    private TableColumn<Request, String> colTieude = new TableColumn<>();
    
    
    
   private ObservableList<Request> model = FXCollections.observableArrayList();
    
   


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
        
		initTable();
		loadTableData();
		
	}
	private void initTable() {
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTieude.setCellValueFactory(new PropertyValueFactory<>("tieude"));
        colNoidung.setCellValueFactory(new PropertyValueFactory<>("noidung"));
        colNgaygui.setCellValueFactory(new PropertyValueFactory<>("ngaygui"));
        colDouutien.setCellValueFactory(new PropertyValueFactory<>("madouutien"));
        colId_nvgui.setCellValueFactory(new PropertyValueFactory<>("manv_gui"));
        colId_nvxuly.setCellValueFactory(new PropertyValueFactory<>("manv_xuly"));
        paneRequestTable.setItems(model);
        
        
        //format lại thời gian theo định dạng ngày tháng năm trên bảng
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        colNgaygui.setCellFactory(column -> {
            return new TableCell<Request, LocalDate>() {
                @Override
                protected void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);

                    if (empty || date == null) {
                        setText(null);
                    } else {
                        setText(formatter.format(date));
                    }
                }
            };
        });
        

	}
//	private void loadTableData() {
//	    
//	    int userId = UserSession.getInstace().getUserId();
//	    List<Request> requests = new RequestModel().findAll(userId);  // Sử dụng userId ở đây
//	    model.addAll(requests);
//	    System.out.println(userId);
//	    
//	}
	
	private void loadTableData() {
	    int userId;  // giá trị mặc định nếu không tìm thấy
	    
	    
	    String currentUsername = UserSession.getInstace().getUsername();
	    AccountModel accountModel = new AccountModel();
	    Account currentUser = accountModel.findByUsername(currentUsername);
	    
	    if(currentUser != null) {
	        userId = currentUser.getId();
	        
	        
	    } else {
	        
	        return;
	    }
	    
	    List<Request> requests = new RequestModel().findAll(userId);
	    model.addAll(requests);
	    
	    
	}



}
