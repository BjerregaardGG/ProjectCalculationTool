package kea.projectcalculationtool.Task;

import jakarta.servlet.http.HttpSession;
import kea.projectcalculationtool.Employee.EmployeeModel;
import kea.projectcalculationtool.Employee.EmployeeService;
import kea.projectcalculationtool.SubProject.SubProjectModel;
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
                             @RequestParam int subProjectId,
                             @RequestParam int employeeId, @RequestParam int projectId) {

        taskService.createTaskAndAddEmployee(task, subProjectId, employeeId);

        return "redirect:/get_task/" + projectId + '/' + subProjectId;
    }

    // shows the tasks for a given subProject
    @GetMapping("/get_task/{projectId}/{subProjectId}")
    public String getTaskBasedOnSubprojectId(@PathVariable int projectId, @PathVariable int subProjectId, Model model) {

        List<TaskModel> priorityTasks = taskService.getTasksSortedByPriority(subProjectId);

        Map<Integer, List<EmployeeModel>> employeesByTask = new HashMap<>();

        for(TaskModel task : priorityTasks) {
            List<EmployeeModel> employees = employeeService.getAllEmployeesByTask(task.getTaskId());
            employeesByTask.put(task.getTaskId(), employees);
        }

        // total hours for a subproject
        int totalHours = 0;

        for(TaskModel task : priorityTasks) {
            if(!task.getTaskStatus()){
                totalHours += task.getDuration();
            }
        }

        model.addAttribute("priorityTasks", priorityTasks);
        model.addAttribute("employeesByTask", employeesByTask);
        model.addAttribute("totalHours", totalHours);

        return "get_task";
    }

    @PostMapping("/task_done/{taskId}")
    public String taskDone(@PathVariable int taskId, @RequestParam("subProjectId") int subProjectId,
                           @RequestParam("projectId") int projectId) {

        taskService.markTaskAsDone(taskId);

        return "redirect:/get_task/" + projectId + '/' + subProjectId;

    }

    @PostMapping("/task_not_done/{taskId}")
    public String taskNotDone(@PathVariable int taskId, @RequestParam("subProjectId") int subProjectId,
                              @RequestParam("projectId") int projectId) {

        taskService.markTaskAsNotDone(taskId);

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
