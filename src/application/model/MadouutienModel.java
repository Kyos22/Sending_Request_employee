package application.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.PreparableStatement;


import application.entities.Madouutien;

import application.model.ConnectDB;


public class MadouutienModel {
	public static List<Madouutien> getAllMadouutien() {
        List<Madouutien> list = new ArrayList<Madouutien>();
        	try {
        		PreparedStatement preparedStatement = ConnectDB.connection()
    					.prepareStatement("select * from douutien");
        		ResultSet resultSet = preparedStatement.executeQuery();
        		while(resultSet.next()) {
					Madouutien madouutien = new Madouutien();
					madouutien.setId(resultSet.getInt("madouutien"));
					madouutien.setName(resultSet.getString("tendouutien"));
					list.add(madouutien);
				}
        		
			} catch (Exception e) {
				list = null;
			}finally {
				ConnectDB.disconnect();
			}
			return list;
       
    }
}
