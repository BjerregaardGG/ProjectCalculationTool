package kea.projectcalculationtool.Employee;

import jakarta.servlet.http.HttpSession;
import kea.projectcalculationtool.Task.TaskService;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpSession;
import kea.projectcalculationtool.Project.ProjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import kea.projectcalculationtool.Project.ProjectRepository;
import kea.projectcalculationtool.Project.ProjectService;

import java.util.List;

@Controller
public class EmployeeController {

    private final TaskService taskService;
    EmployeeService employeeService;
    ProjectService projectService;


    public EmployeeController(EmployeeService employeeService, ProjectService projectService, TaskService taskService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
        this.taskService = taskService;
    }


    @GetMapping("/create_employee")
    public String createEmployee(Model model) {
        model.addAttribute("employee", new EmployeeModel());
        model.addAttribute("roles", EmployeeModel.Roles.values());
        return "create_employee";
    }

    @PostMapping("/create_employee")
    public String createEmployee(@ModelAttribute("employee") EmployeeModel employee, Model model) {
        if(employeeService.findByUsername(employee.getUsername()) || employeeService.findByEmail(employee.getEmail())){
            model.addAttribute("error", "username or email already exists");
            model.addAttribute("roles", EmployeeModel.Roles.values());
            return "/create_employee";
        }
        if(!employee.getPassword().equals(employee.getConfirmPassword())){
            model.addAttribute("passerror", "passwords do not match");
            model.addAttribute("roles", EmployeeModel.Roles.values());
            return "/create_employee";
        }
        employeeService.createEmployee(employee);
        model.addAttribute("sucess", true);
        return "login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("employee", new EmployeeModel());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("employee") EmployeeModel employee, HttpSession session, Model model) {
        try{
            EmployeeModel foundEmployee = employeeService.findEmployee(employee.getUsername(), employee.getPassword());
            if(foundEmployee == null){
                model.addAttribute("error", "username or password does not exist");
                return "login";
            }
            System.out.println("hej");
            Integer EmployeeID = employee.getEmployeeID();
            session.setAttribute("employeeID", foundEmployee.getEmployeeID());
            session.setAttribute("employee", foundEmployee.getUsername());
            session.setAttribute("employeePassword", foundEmployee.getPassword());
            return "redirect:/home";

        } catch (Exception e){
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            return "login";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
    // Adding session and the two models to get all projects and to get projectid from employee so it will only
    // show projects that the employee are bound too.

    @GetMapping("/home")
    public String ShowHomepage(Model model,HttpSession session) {
        Integer EmployeeID = (Integer) session.getAttribute("employeeID");
        // Check if a value was adding with session, if not, no session and therefor you return to login page.
        if(EmployeeID == null){
            return "redirect:/login";
        }
        Integer projectIdBoundToEmployee = projectService.getProjectIdFromEmployeeID(EmployeeID);
        model.addAttribute("projectServ", projectService);
        model.addAttribute("role", projectService.getRoleFromId((EmployeeID)));
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("ProjectIdFromEmployeeId", projectIdBoundToEmployee);
        model.addAttribute("Manager", EmployeeModel.Roles.MANAGER);
        return "homepage";
    }

    // from for adding an employee to a task
    @GetMapping("/add_employee_form/{projectId}/{subProjectId}/{taskId}")
    public String addEmployeeToTaskForm(@PathVariable int projectId, @PathVariable int subProjectId,
                                        @PathVariable int taskId, Model model) {

        EmployeeModel employee = new EmployeeModel();
        List<EmployeeModel> employeesByProject = employeeService.getAllEmployeesByProject(projectId);

        model.addAttribute("employeesByProject", employeesByProject);
        model.addAttribute("subProjectId", subProjectId);
        model.addAttribute("taskId", taskId);
        model.addAttribute("employee", employee);

        return "employee_to_task_form";
    }

    // adds employee to the task
    @PostMapping("/add_employee")
    public String addEmployeeToTask(@RequestParam("subProjectId") int subProjectId,
                                    @RequestParam("employeeId") int employeeId,
                                    @RequestParam("projectId") int projectId, @RequestParam("taskId") int taskId){

        employeeService.addEmployeeToTask(taskId, employeeId);

        return "redirect:/get_task/" + projectId + '/' + subProjectId;

    }

    // from for deleting an employee from a task
    @GetMapping("/delete_employee_form/{projectId}/{subProjectId}/{taskId}")
    public String deleteEmployeeFromTaskForm(@PathVariable int projectId, @PathVariable int subProjectId,
                                        @PathVariable int taskId, Model model) {

        EmployeeModel employee = new EmployeeModel();
        // only employees related to the task
        List<EmployeeModel> employeesByTask = employeeService.getAllEmployeesByTask(taskId);

        model.addAttribute("employeesByTask", employeesByTask);
        model.addAttribute("subProjectId", subProjectId);
        model.addAttribute("taskId", taskId);
        model.addAttribute("employee", employee);

        return "employee_from_task_form";
    }

    // deletes employee to the task
    @PostMapping("/delete_employee")
    public String deleteEmployeeFromTask(@RequestParam("subProjectId") int subProjectId,
                                    @RequestParam("employeeId") int employeeId,
                                    @RequestParam("projectId") int projectId, @RequestParam("taskId") int taskId){

        taskService.deleteEmployeeFromTask(taskId);

        return "redirect:/get_task/" + projectId + '/' + subProjectId;

    }







}
