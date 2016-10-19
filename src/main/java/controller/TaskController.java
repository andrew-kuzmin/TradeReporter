package controller;

import scheduler.Tasks;

public class TaskController {

	private Tasks tasks;

	public TaskController(Tasks tasks) {
		this.tasks = tasks;
	}

	public void startOrderUpdater(){
		tasks.updateOrders();
	}

}
