package com.movies.application.util;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqLiteConnection {
	private Connection connection=null;

	public Connection getConnection() {
		String url="jdbc:sqlite:C:/sqlite/gui/sqlitestudio-3.3.3/SQLiteStudio/movies.db";
		try {
			connection=DriverManager.getConnection(url);
                        if(connection !=null){
                            DatabaseMetaData meta = connection.getMetaData();
                            System.out.println("The driver name is " + meta.getDriverName());
                        }
                        
                        System.out.println("Connection to SQLite has been established.");
		} catch (SQLException e) {
			
			System.out.println(e);
		}
		return connection;
	}
	
}
	