

/*Title     : ToDo Task
Authour     : Priyadharshini.A
Created at  : 09 - Aug -2025
Updated at  : 11 - Aug -2025
Reviewed by :
Reviewed at :
*/
 
 
package com.aspiresys;

import java.util.Scanner;

public class ToDoListMain {
	
	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        UserService userService = new UserService();
	        TaskService taskService = new TaskService(scanner);
	        User user = null;

	        while (true) {
	            try {
	                System.out.println("\n--- ToDo App ---");
	                System.out.println("1. Register");
	                System.out.println("2. Login");
	                System.out.println("3. Exit");
	                System.out.print("Choose: ");

//	                if (!scanner.hasNextLine()) {
//	                    System.out.println("No input provided. Exiting...");
//	                    break;
//	                }

	                String input = scanner.nextLine().trim();

	                if (input.isEmpty()) {
	                    System.out.println("Input is empty, please enter a valid number.");
	                    continue;
	                }

	                int choice;
	                try {
	                    choice = Integer.parseInt(input);
	                } 
	                catch (NumberFormatException exception) {
	                    System.out.println("Invalid input. Please enter a number.");
	                    continue;
	                }

	                if (choice == 1) {
	                    userService.register();
	                } 
	                else if (choice == 2) {
	                    user = userService.login();
	                    if (user != null) break;
	                }
	                else if (choice == 3) {
	                    System.out.println("Goodbye!");
	                    return;
	                } 
	                else {
	                    System.out.println("Invalid choice.");
	                }
	            } catch (Exception exception) {
	                System.out.println("Unexpected error: " + exception.getMessage());
	                break;
	            }
	        }

	        // Task menu
	        while (true) {
	            try {
	                System.out.println("\n--- Task Menu ---");
	                System.out.println("1. Add Task");
	                System.out.println("2. View Pending Tasks");
	                System.out.println("3. Mark Task as Completed");
	                System.out.println("4. View Completed Tasks");
	                System.out.println("5. Logout");
	                System.out.print("Choose: ");

	                if (!scanner.hasNextLine()) {
	                    System.out.println("No input provided. Logging out...");
	                    break;
	                }

	                String input = scanner.nextLine().trim();

	                if (input.isEmpty()) {
	                    System.out.println("Input is empty, please enter a valid number.");
	                    continue;
	                }

	                int option;
	                try {
	                    option = Integer.parseInt(input);
	                } catch (NumberFormatException e) {
	                    System.out.println("Invalid input. Please enter a number.");
	                    continue;
	                }

	                switch (option) {
	                    case 1 -> taskService.addTask(user.getId());
	                    case 2 -> taskService.viewPendingTasks(user.getId());
	                    case 3 -> taskService.markTaskAsCompleted(user.getId());
	                    case 4 -> taskService.viewCompletedTasks(user.getId());
	                    case 5 -> {
	                        System.out.println("Logged out.");
	                        return;
	                    }
	                    default -> System.out.println("Invalid choice.");
	                }
	            } catch (Exception e) {
	                System.out.println("Unexpected error: " + e.getMessage());
	                break;
	            }
	        }

	        scanner.close();
	    }
	}
