package kea.projectcalculationtool.Employee;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("/")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("create_employee")
    public String createEmployee(Model model) {
        model.addAttribute("employee", new EmployeeModel());
        return "employee";
    }

    @PostMapping("create_employee")
    public String createEmployee(@ModelAttribute("employee") EmployeeModel employee, Model model) {
        if(employeeService.findByUsername(employee.getUsername()) || employeeService.findByEmail(employee.getEmail())){
            model.addAttribute("error", "username or email already exists");
            return "create_employee";
        }
        employeeService.createEmployee(employee);
        model.addAttribute("sucess", true);
        return "create_employee";
    }
}
