package kea.projectcalculationtool.Project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("/")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public String getActiveProjects(){
        List<ProjectModel> activeProjects = projectService.getActiveProjects();
        return "projects";
    }



}
