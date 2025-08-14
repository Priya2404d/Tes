package com.aspiresys;

import java.sql.*;
import java.util.Scanner;

public class TaskService {
	
	    private final Scanner scanner;

	    public TaskService(Scanner scanner) {
	        this.scanner = scanner;
	    }

	    public void addTask(int userId) {
	        System.out.print("Enter task description: ");
	        String description = scanner.nextLine().trim();

	        if (description.isEmpty()) {
	            System.out.println("Task description cannot be empty.");
	            return;
	        }

	        String insertQuery = "INSERT INTO tasks(userid, description) VALUES (?, ?)";

	        try (Connection connection = DBConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

	        	statement.setInt(1, userId);
	           // String description;
	            statement.setString(2, description);
	            statement.executeUpdate();

	            System.out.println("Task added successfully.");

	        } catch (SQLException exception) {
	            exception.printStackTrace();
	        }
	    }

	    public void viewPendingTasks(int userId) {
	        String query = "SELECT taskid, description FROM tasks WHERE userid = ?";
	        boolean hasTasks = false;

	        try (Connection connetion = DBConnection.getConnection();
	             PreparedStatement stmt = connetion.prepareStatement(query)) {

	            stmt.setInt(1, userId);
	            try (ResultSet resultset = stmt.executeQuery()) {

	                System.out.println("Your Pending Tasks:");
	                while (resultset .next()) {
	                    hasTasks = true;
	                    System.out.printf("%d. %s\n", resultset.getInt("taskid"), resultset.getString("description"));
	                }
	            }

	            if (!hasTasks) {
	                System.out.println("No pending tasks.");
	            }

	        } catch (SQLException exception) {
	        	exception.printStackTrace();
	        }
	    }

	    public void markTaskAsCompleted(int userId) {
	        viewPendingTasks(userId);

	        System.out.print("Enter task ID to mark as completed: ");
	        int taskId;

	        try {
	            taskId = Integer.parseInt(scanner.nextLine().trim());
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid task ID. Please enter a number.");
	            return;
	        }

	        String selectQuery = "SELECT description FROM tasks WHERE taskid = ? AND userid = ?";
	        String insertQuery = "INSERT INTO completed_tasks(userid, description) VALUES (?, ?)";
	        String deleteQuery = "DELETE FROM tasks WHERE taskid = ?";

	        try (Connection connection = DBConnection.getConnection()) {

	            // Get task description
	            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
	                selectStmt.setInt(1, taskId);
	                selectStmt.setInt(2, userId);
	                try (ResultSet resultset = selectStmt.executeQuery()) {
	                    if (!resultset.next()) {
	                        System.out.println("Task not found or not yours.");
	                        return;
	                    }

	                    String description = resultset.getString("description");

	                    // Insert into CompletedTask
	                    try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
	                        insertStmt.setInt(1, userId);
	                        insertStmt.setString(2, description);
	                        insertStmt.executeUpdate();
	                    }

	                    // Delete from Task
	                    try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
	                        deleteStmt.setInt(1, taskId);
	                        deleteStmt.executeUpdate();
	                    }

	                    System.out.println("Task marked as completed.");
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public void viewCompletedTasks(int userId) {
	        String query = "SELECT taskid, description, completed_at FROM completed_tasks WHERE userid = ?";
	        boolean hasTasks = false;

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query)) {

	            stmt.setInt(1, userId);
	            try (ResultSet rs = stmt.executeQuery()) {

	                System.out.println("Your Completed Tasks:");
	                while (rs.next()) {
	                    hasTasks = true;
	                    System.out.printf("%d. %s (Completed on: %s)\n",
	                            rs.getInt("taskid"),
	                            rs.getString("description"),
	                            rs.getTimestamp("completed_at").toString());
	                }
	            }

	            if (!hasTasks) {
	                System.out.println("No completed tasks.");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
