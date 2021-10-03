package com.movies.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.movies.application.util.SqLiteConnection;

public class MovieApplication {
	
	
	public static void main(String[] args) {
		SqLiteConnection sqLiteConnection = new SqLiteConnection();
                Scanner scanner = new Scanner(System.in);
		try(Connection connection = sqLiteConnection.getConnection()){
			
                    //we are creating the table in this method if the table doesnot exist.
		    createMoviesTable(connection);
		    
                    //we are inserting the data in this method
		    insertMovies(connection,scanner);
		    
                    // we are retrieving all the movies present in the database.
		    getAllMovies(connection);
		    
                    // we are retrieving the movies based on the actor name.
		    getMoviesWithActorName(connection,scanner);
			
		    connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
                scanner.close();
	}

	private static void getMoviesWithActorName(Connection connection, Scanner scanner) throws SQLException {
		System.out.println("Enter the Actor name to search:");
		String actor = scanner.next();
		
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("SELECT MOVIE_NAME, ACTOR_NAME, ACTRESS_NAME, YEAR_OF_RELEASE, DIRECTOR_NAME FROM MOVIES WHERE ACTOR_NAME = ?");
		
		PreparedStatement statement = connection.prepareStatement(selectQuery.toString());
		statement.setString(1, actor);
		ResultSet resultSet = statement.executeQuery();
		if(resultSet == null) {
			System.out.println("No records found");
		}
		while (resultSet.next()) {

			String movieName = resultSet.getString(1);
			String actorName = resultSet.getString(2);
			String actressName = resultSet.getString(3);
			int yearOfRelease = resultSet.getInt(4);
			String directorName = resultSet.getString(5);
			System.out.println(movieName + "\t" + actorName + "\t" + actressName + "\t" + yearOfRelease + "\t" + directorName);
		}
	}

	private static void createMoviesTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		StringBuilder createQuery = new StringBuilder();
		createQuery.append("CREATE TABLE IF NOT EXISTS MOVIES (\n");
		createQuery.append("MOVIE_NAME VARCHAR2(20) PRIMARY KEY,\n");
		createQuery.append("ACTOR_NAME VARCHAR2(20),\n");
		createQuery.append("ACTRESS_NAME VARCHAR2(20),\n");
		createQuery.append("YEAR_OF_RELEASE NUMBER,\n");
		createQuery.append("DIRECTOR_NAME VARCHAR2(20)");
		createQuery.append(");");
		
		statement.execute(createQuery.toString());
		
	}

	private static void insertMovies(Connection connection,Scanner scan) throws SQLException {
		boolean flag = true;
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO MOVIES(MOVIE_NAME,ACTOR_NAME,ACTRESS_NAME,YEAR_OF_RELEASE,DIRECTOR_NAME)");
		insertQuery.append("values(?,?,?,?,?) ");
		while(flag) {
                    System.out.println("Enter the Movie name:");
                    String movieName = scan.next();
                    System.out.println("Enter the actor name:");
                    String actorName = scan.next();
                    System.out.println("Enter the actress name:");
                    String actressName = scan.next();
                    System.out.println("Enter the year of release:");
                    int yearOfRelease = scan.nextInt();
                    System.out.println("Enter the director name:");
                    String directorName = scan.next();
                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery.toString());
                    preparedStatement.setString(1,movieName);
                    preparedStatement.setString(2,actorName);
                    preparedStatement.setString(3,actressName);
                    preparedStatement.setInt(4,yearOfRelease);
                    preparedStatement.setString(5,directorName);
		
                    preparedStatement.executeUpdate();
                    System.out.println("do you want to add another movie(Y/N)/(y/n):");
                    String value = scan.next();
                    if(value.equals("Y") || value.equals("y")) {
			flag = true;
                    }else if(value.equals("N") || value.equals("n")) {
			flag = false;
                    }else {
			System.out.println("invalid input");
                        break;
		  }
		}
	}

	private static void getAllMovies(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("SELECT MOVIE_NAME, ACTOR_NAME, ACTRESS_NAME, YEAR_OF_RELEASE, DIRECTOR_NAME FROM MOVIES");
		
		ResultSet resultSet = statement.executeQuery(selectQuery.toString());
		
		while(resultSet.next()) {
                    String movieName = resultSet.getString(1);
                    String actorName = resultSet.getString(2);
                    String actressName = resultSet.getString(3);
                    int yearOfRelease = resultSet.getInt(4);
                    String directorName = resultSet.getString(5);
                    System.out.println(movieName + "\t" + actorName+ "\t"+ actressName+ "\t"+ yearOfRelease + "\t"+ directorName);
		}
		
	}

}
