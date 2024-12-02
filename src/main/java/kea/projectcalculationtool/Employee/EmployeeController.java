package kea.projectcalculationtool.Employee;

import jakarta.servlet.http.HttpSession;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
import kea.projectcalculationtool.Project.ProjectRepository;
import kea.projectcalculationtool.Project.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    ProjectService projectService;

    public EmployeeController(ProjectService projectService) {
        this.projectService = projectService;
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
            return "create_employee";
        }
        if(!employee.getPassword().equals(employee.getConfirmPassword())){
            model.addAttribute("passerror", "passwords do not match");
            return "create_employee";
        }
        employeeService.createEmployee(employee);
        model.addAttribute("sucess", true);
        return "create_employee";
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
            session.setAttribute("employeeID", EmployeeID);
            session.setAttribute("employee", foundEmployee.getUsername());
            session.setAttribute("employeePassword", employee.getPassword());
            return "redirect:/" + foundEmployee.getUsername();

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
    @GetMapping("/home")
    public String ShowHomepage(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "homepage";
    }
}
