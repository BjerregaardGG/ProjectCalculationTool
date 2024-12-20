package kea.projectcalculationtool.Task;

import jakarta.servlet.http.HttpSession;
import kea.projectcalculationtool.Employee.EmployeeModel;
import kea.projectcalculationtool.Employee.EmployeeService;
import kea.projectcalculationtool.Project.ProjectModel;
import kea.projectcalculationtool.Project.ProjectService;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

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
      @RequestParam int employeeId,
      @RequestParam int projectId, RedirectAttributes redirectAttributes) {

    if (task.getTaskStartDate() != null && task.getTaskStartDate().isAfter(task.getTaskDeadline())) {
      redirectAttributes.addFlashAttribute("TimeError", true);
      return "redirect:/task_form/project/" + projectId + "/" + subProjectId;
    }

    taskService.createTaskAndAddEmployee(task, subProjectId, employeeId);

    return "redirect:/get_task/" + projectId + '/' + subProjectId;
  }

  // shows the tasks for a given subProject
  @GetMapping("/get_task/{projectId}/{subProjectId}")
  public String getTaskBasedOnSubprojectId(@PathVariable int projectId, @PathVariable int subProjectId, Model model) {

    // saves the function and variables from service in a Map
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
  //Create a static HashSet so it only gets unique employees
  public static Set<String> historicalTaskEmployees = new HashSet<>();
  // marks a task as done
  @PostMapping("/task_done/{taskId}")
  public String taskDone(@PathVariable int taskId, @RequestParam("subProjectId") int subProjectId,
      @RequestParam("projectId") int projectId) {

    // Get current employees for the task
    List<EmployeeModel> currentEmployees = projectService.getAllEmployeesInTask(taskId);

    // Save unique task-employee combinations so they can be used to calculate.
    for (EmployeeModel employee : currentEmployees) {
      historicalTaskEmployees.add(taskId + "-" + employee.getEmployeeID());
    }

    taskService.markTaskAsDone(taskId);

    TaskModel task = taskService.getTask(taskId);

    // if task is done, then remove employees from the task
    if (task.getTaskStatus()) {
      taskService.deleteEmployeeFromTask(taskId);
    }

    return "redirect:/get_task/" + projectId + '/' + subProjectId;

  }

  // marks the task as not done
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

    @GetMapping("/updatetask/{taskId}")
    public String updatetaskForm(@PathVariable("taskId") Integer taskId, Model model, HttpSession session) {
        Integer EmployeeID = (Integer) session.getAttribute("employeeID");
        if(EmployeeID == null){
            return "redirect:/login";
        }
        if(taskId == null){
            System.out.println("taskId is null");
            return "get_task";
        }

        TaskModel task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);

        return "updatetask";
    }
    @PostMapping("/updatetask/{taskId}")
    public String submitUpdateSubProject(@ModelAttribute TaskModel task) {
        taskService.updateTask(task);
        //todo find projekt id for dette subprojekt og returner det i variabel "projectId"
        int subProjectId = task.getSubProjectId();
        int projectId = taskService.getProjectIdBySubProjectId(subProjectId);
        return "redirect:/get_task/" + projectId + '/' + subProjectId;
    }
}
