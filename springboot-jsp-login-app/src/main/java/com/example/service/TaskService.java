package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Task;
import com.example.repository.TaskRepository;

@Service
public class TaskService implements ITaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public List<Task> getTasksByUser(String user) {
		return taskRepository.findByUserName(user);
	}

	@Override
	public Optional<Task> getTasksById(long id) {
		return taskRepository.findById(id);
	}

	@Override
	public void updateTask(Task task) {
		taskRepository.save(task);
	}

	@Override
	public void addTask(String name, String desc, Date targetDate, boolean isDone) {
		taskRepository.save(new Task(name, desc, targetDate, isDone));
	}

	@Override
	public void deleteTask(long id) {
		Optional<Task> todo = taskRepository.findById(id);
		if (todo.isPresent()) {
			taskRepository.delete(todo.get());
		}
	}

	@Override
	public void saveTask(Task task) {
		taskRepository.save(task);
	}
}
