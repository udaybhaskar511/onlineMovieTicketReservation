package com.movie.ticket.sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionUtils {
	
	public static Connection connection = null;  
    public static PreparedStatement preparedStatement = null;  
    public static ResultSet resultSet = null; 
	
	public static Connection getConnection(){
		
		//creating connection to database
		String connectionURL = "";
		
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(connectionURL);
						
		}catch(ClassNotFoundException e){
			e.getMessage();
			
		}catch(SQLException e){
			
			e.printStackTrace();
		}
		
		return connection;
	}



	public static void closeAll(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){
		
		if(connection != null){
			try{
				
				connection.close();
				
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		if(preparedStatement != null){
			try{
				
				preparedStatement.close();
				
			}catch(SQLException e){
				e.printStackTrace();
			}
			
		}
		
		if(resultSet != null){
			try{
				
				resultSet.close();
				
			}catch(SQLException e){
				e.printStackTrace();
			}
			
		}
		
	}

}
