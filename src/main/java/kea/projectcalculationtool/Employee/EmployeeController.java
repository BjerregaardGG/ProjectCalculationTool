package kea.projectcalculationtool.Employee;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {

    @GetMapping("/home")
    public String ShowHomepage(){
        return "homepage";
    }
}
