package kea.projectcalculationtool.Project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {

    ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

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

    @GetMapping("/activeProjects")
    public String getActiveProjects(Model model){
        List<ProjectModel> activeProjects = projectService.getActiveProjects();
        model.addAttribute("projects", activeProjects);
        return "activeProjects";
    }

}
