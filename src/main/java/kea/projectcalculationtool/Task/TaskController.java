package kea.projectcalculationtool.Task;

import kea.projectcalculationtool.Employee.EmployeeModel;
import kea.projectcalculationtool.Employee.EmployeeService;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller("/")
public class TaskController {

    TaskService taskService;
    EmployeeService employeeService;

    public TaskController(TaskService taskService, EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.taskService = taskService;
    }

    // shows the task form for a given subProject
    @GetMapping("/task_form/project/{projectId}/{subProjectId}")
    public String showCreateTaskForm(@PathVariable int projectId, @PathVariable int subProjectId, Model model) {

        TaskModel task = new TaskModel();
        List<EmployeeModel> employeesByProject = employeeService.getAllEmployeesByProject(projectId);

        model.addAttribute("task", task);
        model.addAttribute("subProjectId", subProjectId);
        model.addAttribute("employeesByProject", employeesByProject);

        return "create_task";
    }

    // creates the task and connects it to the subProjectId with @RequestParam
    @PostMapping("/create_task")
    public String createTask(@ModelAttribute TaskModel task,
                             @RequestParam("subProjectId") int subProjectId,
                             @RequestParam("employeeId") int employeeId) {

        System.out.println("Received employeeId: " + employeeId);
        taskService.createTaskAndAddEmployee(task, subProjectId, employeeId);


        return "redirect:/get_task" + subProjectId;
    }

    @GetMapping("/get_task/{subProjectId}")
    public String getTaskBasedOnSubprojectId(@PathVariable int subProjectId, Model model) {

        List<TaskModel> priorityTasks = taskService.getTasksSortedByPriority(subProjectId);

        model.addAttribute("priorityTasks", priorityTasks);

        return "get_task";
    }



}
