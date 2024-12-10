package kea.projectcalculationtool.Task;

import kea.projectcalculationtool.Employee.EmployeeModel;
import kea.projectcalculationtool.Employee.EmployeeService;
import kea.projectcalculationtool.Project.ProjectModel;
import kea.projectcalculationtool.Project.ProjectService;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/")
public class TaskController {

    TaskService taskService;
    EmployeeService employeeService;
    ProjectService projectService;

    public TaskController(TaskService taskService, EmployeeService employeeService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.taskService = taskService;
        this.projectService = projectService;
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
                             @RequestParam int subProjectId,
                             @RequestParam int employeeId, @RequestParam int projectId) {

        taskService.createTaskAndAddEmployee(task, subProjectId, employeeId);

        return "redirect:/get_task/" + projectId + '/' + subProjectId;
    }

    // shows the tasks for a given subProject
    @GetMapping("/get_task/{projectId}/{subProjectId}")
    public String getTaskBasedOnSubprojectId(@PathVariable int projectId, @PathVariable int subProjectId, Model model) {

        Map<String, Object> taskData = taskService.getTaskSortedByPriority(subProjectId);

        ProjectModel project = projectService.getProjectById(projectId);


        // Tilføj dataene til model
        model.addAttribute("project", project);
        model.addAttribute("totalHours", taskData.get("totalHours"));
        model.addAttribute("priorityTasks", taskData.get("priorityTasks"));
        model.addAttribute("employeesByTask", taskData.get("employeesByTask"));
        model.addAttribute("hoursPrEmployee", taskData.get("hoursPrEmployee"));

        return "get_task";
    }

    @PostMapping("/task_done/{taskId}")
    public String taskDone(@PathVariable int taskId, @RequestParam("subProjectId") int subProjectId,
                           @RequestParam("projectId") int projectId) {

        taskService.markTaskAsDone(taskId);

        TaskModel task = taskService.getTask(taskId);

        if(task.getTaskStatus()) {
            taskService.deleteEmployeeFromTask(taskId);
        }

        return "redirect:/get_task/" + projectId + '/' + subProjectId;

    }

    @PostMapping("/task_not_done/{taskId}")
    public String taskNotDone(@PathVariable int taskId, @RequestParam("subProjectId") int subProjectId,
                              @RequestParam("projectId") int projectId) {

        taskService.markTaskAsNotDone(taskId);

        return "redirect:/get_task/" + projectId + '/' + subProjectId;
    }

    @PostMapping("/delete_task/{taskId}")
    public String deleteTask(@PathVariable int taskId, @RequestParam("projectId") int projectId,
                             @RequestParam("subProjectId") int subProjectId) {

        projectService.deleteTask(taskId);

        return "redirect:/get_task/" + projectId + '/' + subProjectId;
    }

}
