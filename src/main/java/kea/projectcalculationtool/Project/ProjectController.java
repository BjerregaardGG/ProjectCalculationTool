package kea.projectcalculationtool.Project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProjectController {

    ProjectService projectService;

    @GetMapping("/create_project")
    public String createProject(Model model) {
        model.addAttribute("project", new ProjectModel());
        return "create_project";
    }

    @PostMapping("/create_project")
    public String createNewProject(@ModelAttribute ("project") ProjectModel project){
        projectService.createProject(project);
        return "redirect:/";
    }

    @GetMapping("/project/{projectId}/time")
    public String showProjectTime(@PathVariable int projectId,Model model) {
        double totalTime = projectService.calculateTime(projectId);
        model.addAttribute("totalTime", totalTime);
        return "some-project-page";
    }
}
