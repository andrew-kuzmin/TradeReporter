package controller;

import org.springframework.stereotype.Controller;
import scheduler.Tasks;

@Controller
public class TaskController {

	private Tasks tasks;

	public TaskController(Tasks tasks) {
		this.tasks = tasks;
	}

	public void startOrderUpdater(){
		tasks.updateOrders();
	}

}
