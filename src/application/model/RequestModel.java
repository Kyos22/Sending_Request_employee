
package application.model;




import application.entities.Request;
import application.model.ConnectDB;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequestModel {

    

//    public static List<Request> findAll() {
//    	List<Request> requests = new ArrayList<Request>();
//		try {
//			PreparedStatement preparedStatement = ConnectDB.connection()
//				.prepareStatement("select * from yeucau");
//			ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                Request request = new Request();
//                request.setId(resultSet.getInt("mayeucau"));
//                request.setTieude(resultSet.getString("tieude"));
//                request.setNoidung(resultSet.getString("noidung"));
//                request.setNgaygui(resultSet.getObject("ngaygui", LocalDate.class));
//                request.setMadouutien(resultSet.getInt("madouutien"));
//                request.setManv_gui(resultSet.getInt("manv_gui"));
//                request.setManv_xuly(resultSet.getInt("manv_xuly"));
//
//                requests.add(request);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return requests;
//    }
	public static List<Request> findAll(int userId) {
    	List<Request> requests = new ArrayList<Request>();
		try {
			PreparedStatement preparedStatement = ConnectDB.connection()
				.prepareStatement("select * from yeucau where manv_gui = ?");
			preparedStatement.setInt(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Request request = new Request();
                request.setId(resultSet.getInt("mayeucau"));
                request.setTieude(resultSet.getString("tieude"));
                request.setNoidung(resultSet.getString("noidung"));
                request.setNgaygui(resultSet.getObject("ngaygui", LocalDate.class));
                request.setMadouutien(resultSet.getInt("madouutien"));
                request.setManv_gui(resultSet.getInt("manv_gui"));
                request.setManv_xuly(resultSet.getInt("manv_xuly"));

                requests.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requests;
    }
    public static boolean create(Request request) {
	    boolean result = true;
	    try {
	        PreparedStatement preparedStatement = ConnectDB.connection()
	            .prepareStatement("insert into yeucau(tieude, noidung,ngaygui, madouutien, manv_gui, manv_xuly) values(?,?,?,?,?,?)", java.sql.Statement.RETURN_GENERATED_KEYS);
	        preparedStatement.setString(1, request.getTieude());
	        preparedStatement.setString(2, request.getNoidung());
	        preparedStatement.setDate(3, java.sql.Date.valueOf(request.getNgaygui()));
	        preparedStatement.setInt(4, request.getMadouutien());
	        preparedStatement.setInt(5, request.getManv_gui());
	        preparedStatement.setInt(6, 0);
	        result = preparedStatement.executeUpdate()>0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        result = false;
	    } finally {
	        ConnectDB.disconnect();
	    }
	    return result;
	}
    public static List<Request> findBetweenDates(int userId,LocalDate startDate, LocalDate endDate) {
    	List<Request> requests = new ArrayList<Request>();
		try {
			PreparedStatement preparedStatement = ConnectDB.connection()
				.prepareStatement("select * from yeucau where manv_gui = ? and ngaygui >= ? and ngaygui <= ?");
			preparedStatement.setInt(1, userId);			
			preparedStatement.setDate(2, Date.valueOf(startDate));
	        preparedStatement.setDate(3, Date.valueOf(endDate));								
			
			ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Request request = new Request();
                request.setId(resultSet.getInt("mayeucau"));
                request.setTieude(resultSet.getString("tieude"));
                request.setNoidung(resultSet.getString("noidung"));
                request.setNgaygui(resultSet.getObject("ngaygui", LocalDate.class));
                request.setMadouutien(resultSet.getInt("madouutien"));
                request.setManv_gui(resultSet.getInt("manv_gui"));
                request.setManv_xuly(resultSet.getInt("manv_xuly"));

                requests.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requests;
    }
    public static List<Request> findByMadouutien(int userId,int madouutien) {
    	List<Request> requests = new ArrayList<Request>();
    	try {
    		PreparedStatement preparedStatement = ConnectDB.connection()
    				.prepareStatement("select * from yeucau where manv_gui = ? and madouutien = ?");
    		preparedStatement.setInt(1, userId);
    		preparedStatement.setInt(2, madouutien);
    		
    		
    		ResultSet resultSet = preparedStatement.executeQuery();
    		while (resultSet.next()) {
    			Request request = new Request();
    			request.setId(resultSet.getInt("mayeucau"));
    			request.setTieude(resultSet.getString("tieude"));
    			request.setNoidung(resultSet.getString("noidung"));
    			request.setNgaygui(resultSet.getObject("ngaygui", LocalDate.class));
    			request.setMadouutien(resultSet.getInt("madouutien"));
    			request.setManv_gui(resultSet.getInt("manv_gui"));
    			request.setManv_xuly(resultSet.getInt("manv_xuly"));
    			
    			requests.add(request);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return requests;
    }
    
    

    // Bạn có thể thêm các phương thức khác như insertRequest(), updateRequest(), deleteRequest(), ...
}
