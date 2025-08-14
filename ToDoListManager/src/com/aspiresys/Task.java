package com.aspiresys;

public class Task {

	    private int taskId;
	    private int userId;
	    private String description;
	    private String status;

	    public Task(int taskId, int userId, String description, String status) {
	        this.taskId = taskId;
	        this.userId = userId;
	        this.description = description;
	        this.status = status;
	    }

	    public int getId() {
	        return taskId;
	    }

	    public int getUserId() {
	        return userId;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public String getStatus() {
	        return status;
	    }
	}
