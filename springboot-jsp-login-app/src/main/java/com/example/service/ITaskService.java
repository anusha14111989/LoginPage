package com.example.service;

import java.util.Date;

import java.util.List;
import java.util.Optional;

import com.example.model.Task;

public interface ITaskService {

	List<Task> getTasksByUser(String user);

	Optional<Task> getTasksById(long id);

	void updateTask(Task task);

	void addTask(String name, String desc, Date targetDate, boolean isDone);

	void deleteTask(long id);
	
	void saveTask(Task task);

}