package kea.projectcalculationtool.Employee;

import kea.projectcalculationtool.Project.ProjectRepository;
import kea.projectcalculationtool.Project.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {
    ProjectService projectService;

    public EmployeeController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @GetMapping("/home")
    public String ShowHomepage(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "homepage";
    }
}
