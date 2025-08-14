package com.aspiresys;

import java.sql.*;
import java.util.Scanner;

public class UserService {
	    
	    private final Scanner scanner = new Scanner(System.in);

	    public void register() {
	        try (Connection connection = DBConnection.getConnection()) {
	            System.out.print("Enter username (min 4 alphanumeric chars): ");
	            String username = scanner.nextLine().trim();

	            // Username validation
	            if (!username.matches("[a-zA-Z0-9]{4,}")) {
	                System.out.println("Invalid username. Must be at least 4 alphanumeric characters.");
	                return;
	            }

	            // Check if user exists
	            String checkQuery = "SELECT id FROM users WHERE username = ?";
	            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
	            	checkStatement.setString(1, username);
	                try (ResultSet resultset = checkStatement.executeQuery()) {
	                    if (resultset.next()) {
	                        System.out.println("Username already taken.");
	                        return;
	                    }
	                }
	            }

	            // Read and validate password
	            System.out.print("Enter password (min 6 chars, at least one letter and one digit): ");
	            String password = scanner.nextLine().trim();

	            if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d).{6,}$")) {
	                System.out.println("Invalid password. Must be at least 6 characters with at least one letter and one digit.");
	                return;
	            }

	            // Insert new user
	            String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
	            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
	            	insertStatement.setString(1, username);
	            	insertStatement.setString(2, password); 
	            	insertStatement.executeUpdate();
	                System.out.println("User registered successfully.");
	            }

	        } 
	        catch (SQLException exception) {
	        	exception.printStackTrace();
	        }
	    }

	    public User login() {
	        try (Connection connection = DBConnection.getConnection()) {
	            System.out.print("Enter username: ");
	            String username = scanner.nextLine().trim();

	            System.out.print("Enter password: ");
	            String password = scanner.nextLine().trim();

	            String query = "SELECT id FROM users WHERE username = ? AND password = ?";
	            try (PreparedStatement statement = connection.prepareStatement(query)) {
	            	statement.setString(1, username);
	            	statement.setString(2, password);
	                try (ResultSet resultset = statement.executeQuery()) {
	                    if (resultset.next()) {
	                        int userId = resultset.getInt("id");
	                        System.out.println("Login successful.");
	                        return new User(userId, username, password);
	                    } else {
	                        System.out.println("Invalid username or password.");
	                        return null;
	                    }
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	}
