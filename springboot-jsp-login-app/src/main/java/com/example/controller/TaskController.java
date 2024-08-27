package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Task;
import com.example.service.ITaskService;

@Controller
public class TaskController {

	@Autowired
	private ITaskService taskService;

//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		// Date - dd/MM/yyyy
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
//	}

	@GetMapping("/list-todos")
	public String showTodos(ModelMap model) {
		String name = getLoggedInUserName(model);
		model.put("tasks", taskService.getTasksByUser(name));
		// model.put("todos", service.retrieveTodos(name));
		return "list-todos";
	}

	private String getLoggedInUserName(ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}

		return principal.toString();
	}

	@GetMapping("/add-task")
	public String showAddTodoPage(ModelMap model) {
		model.addAttribute("task", new Task());
		return "task";
	}

	@GetMapping("/delete-task")
	public String deleteTodo(@RequestParam long id) {
		taskService.deleteTask(id);
		// service.deleteTodo(id);
		return "redirect:/list-todos";
	}

	@GetMapping("/update-task")
	public String showUpdateTodoPage(@RequestParam long id, ModelMap model) {
		Task task = taskService.getTasksById(id).get();
		model.put("task", task);
		return "todo";
	}

	@PostMapping("/update-task")
	public String updateTodo(ModelMap model, @Valid Task task, BindingResult result) {

		if (result.hasErrors()) {
			return "task";
		}

		task.setUserName(getLoggedInUserName(model));
		taskService.updateTask(task);
		return "redirect:/list-todos";
	}

	@PostMapping("/add-todo")
	public String addTodo(ModelMap model, @Valid Task task, BindingResult result) {

		if (result.hasErrors()) {
			return "task";
		}

		task.setUserName(getLoggedInUserName(model));
		taskService.saveTask(task);
		return "redirect:/list-tasks";
	}
}
