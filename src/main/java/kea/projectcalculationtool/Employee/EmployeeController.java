package kea.projectcalculationtool.Employee;

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
    @GetMapping("/home")
    public String ShowHomepage(Model model) {
        model.addAttribute("projects", projectRepository.getAllProjects());
        return "homepage";
    }
}
