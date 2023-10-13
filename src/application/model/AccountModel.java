package application.model;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.Blob;


import application.entities.Account;


public class AccountModel {
	
	public static boolean create(Account account) {
		boolean result = true;
		try {
			PreparedStatement preparedStatement = ConnectDB.connection()
				.prepareStatement("insert into nhanvien(username, password, hoten, ngaysinh, hinhanh,verification_code) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, account.getUsername());
			preparedStatement.setString(2, account.getPassword());
			preparedStatement.setString(3, account.getHoten());
			
			Date sqlDate = Date.valueOf(account.getNgaysinh());
			preparedStatement.setDate(4, sqlDate);
			
			byte[] byteArray = account.getHinhanh();
			preparedStatement.setBytes(5, byteArray);
			preparedStatement.setString(6, account.getVerificationCode());
			
			result = preparedStatement.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			ConnectDB.disconnect();
		}
		return result;
	}
	
	
	
	
	public Account findByUsername(String username) {
	    Account account = null;
	    try {
	        PreparedStatement preparedStatement = ConnectDB.connection()
	            .prepareStatement("SELECT * FROM nhanvien WHERE username = ?");
	        preparedStatement.setString(1, username);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            account = new Account();
	            account.setId(resultSet.getInt("id"));
	            account.setUsername(resultSet.getString("username"));
	            account.setPassword(resultSet.getString("password"));
	            account.setHinhanh(resultSet.getBytes("hinhanh"));
	            account.setHoten(resultSet.getString("hoten"));
	            Date date = resultSet.getDate("ngaysinh");
	            if (date != null) {
	                account.setNgaysinh(date.toLocalDate());
	            }
	            account.setVerificationCode(resultSet.getString("verification_code"));
	            
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        ConnectDB.disconnect();
	    }
	    return account;
	}
	 public boolean updateWithoutPassword(Account user) {
		 boolean result = true;
			try {
				PreparedStatement preparedStatement = ConnectDB.connection()
					.prepareStatement("update nhanvien set username = ?,hoten = ?, ngaysinh = ?, hinhanh = ? where username = ?");
				preparedStatement.setString(1, user.getUsername());
				preparedStatement.setString(2, user.getHoten());
	
				Date sqlDate = Date.valueOf(user.getNgaysinh());
				preparedStatement.setDate(3, sqlDate);
				
				byte[] byteArray = user.getHinhanh();
				preparedStatement.setBytes(4, byteArray);
		        
		        preparedStatement.setString(5, user.getUsername());
				
				result = preparedStatement.executeUpdate() > 0;
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			} finally {
				ConnectDB.disconnect();
			}
			return result;
	    }
	 public static List<Account> findAll() {
			List<Account> accounts = new ArrayList<Account>();
			try {
				PreparedStatement preparedStatement = ConnectDB.connection()
					.prepareStatement("select * from nhanvien");
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next()) {
					Account account = new Account();
					account.setId(resultSet.getInt("id"));
					account.setUsername(resultSet.getString("username"));
					//lấy 2 thuộc tính trước, sau này càn gì thì thêm vào
					accounts.add(account);
				}
			} catch (Exception e) {
				accounts = null;
			} finally {
				ConnectDB.disconnect();
			}
			return accounts;
		}

}
