package application;

import java.awt.Button;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import application.entities.Madouutien;
import application.entities.Request;
import application.entities.UserSession;
import application.model.MadouutienModel;
import application.model.RequestModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class SearchPageController implements Initializable {
	
	@FXML
	private ComboBox<Madouutien> comboboxmadouutien;
	
	@FXML
	private DatePicker dateFrom;
	
	@FXML
	private DatePicker dateTo;
	
	@FXML
	private javafx.scene.control.Button buttonSearchDate;

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
		loadMadouutien();
		buttonSearchDate.setOnAction(e -> searchByDate());
		comboboxmadouutien.setOnAction(e -> searchByMadouutien());

		
		// Format date pickers
	    formatDatePicker(dateFrom);
	    formatDatePicker(dateTo);

		
	
		
		
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
		private void loadTableData() {
	    
	    int userId = UserSession.getInstace().getUserId();
	    List<Request> requests = new RequestModel().findAll(userId);  // Sử dụng userId ở đây
	    model.addAll(requests);
	}
	
	@FXML
	void searchByDate() {
		
		int userId = UserSession.getInstace().getUserId();
        LocalDate startDate = dateFrom.getValue();
        LocalDate endDate = dateTo.getValue();
        if (startDate != null && endDate != null && !startDate.isAfter(endDate)) {
            List<Request> requests1 = new RequestModel().findBetweenDates(userId, startDate, endDate);
            model.clear();
            model.addAll(requests1);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("loi roi dkm");
            alert.show();
        }													
	}
	private void searchByMadouutien() {
		int userId = UserSession.getInstace().getUserId();
	    Madouutien selectedMadouutien = comboboxmadouutien.getSelectionModel().getSelectedItem();
	    if (selectedMadouutien != null) {
	        List<Request> requests = new RequestModel().findByMadouutien(userId,selectedMadouutien.getId()); // giả định rằng bạn có một phương thức findByMadouutien trong RequestModel
	        model.clear();
	        model.addAll(requests);
	    } else {
	        System.out.println("No priority level selected.");
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
	private void loadMadouutien() {
        MadouutienModel model = new MadouutienModel();
        List<Madouutien> list = model.getAllMadouutien();        
        comboboxmadouutien.getItems().addAll(list);
        }


}
