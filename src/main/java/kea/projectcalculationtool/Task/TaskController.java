package kea.projectcalculationtool.Task;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("/")
public class TaskController {

    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // shows the task form for a given subProject
    @GetMapping("/task_form/{subProjectId}")
    public String showCreateTaskForm(@PathVariable int subProjectId, Model model) {

        TaskModel task = new TaskModel();

        model.addAttribute("task", task);
        model.addAttribute("subProjectId", subProjectId);

        return "create_task";
    }

    // creates the task and connects it to the subProjectId with @RequestParam
    @PostMapping("/create_task")
    public String createTask(@ModelAttribute TaskModel task, @RequestParam("subProjectId") int id) {
        taskService.createTask(task, id);

        return "redirect:/";
    }

    @GetMapping("/get_task/{id}")
    public String getTask(@PathVariable int id, Model model) {
        TaskModel task = taskService.getTask(id);

        model.addAttribute("task", task);

        return "get_task";
    }


}
