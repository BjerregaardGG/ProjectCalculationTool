package kea.projectcalculationtool.Employee;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpSession;
import kea.projectcalculationtool.Project.ProjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {
    ProjectRepository projectRepository;
    public EmployeeController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    //Adding session and the two models to get all projects and to get projectid from employee so it will only
    // show projects that the employee are bound too.
    @GetMapping("/home")
    public String ShowHomepage(Model model,HttpSession session) {
        Integer EmployeeID = (Integer) session.getAttribute("EmployeeID");
        model.addAttribute("projects", projectRepository.getAllProjects());
        model.addAttribute("ProjectOwner", projectRepository.getProjectIdFromEmployeeID(EmployeeID));
        return "homepage";
    }
}
