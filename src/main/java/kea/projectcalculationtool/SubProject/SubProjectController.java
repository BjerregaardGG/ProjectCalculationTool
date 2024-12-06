package kea.projectcalculationtool.SubProject;

import kea.projectcalculationtool.Project.ProjectModel;
import kea.projectcalculationtool.Project.ProjectService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class SubProjectController {

    SubProjectService subProjectService;

    ProjectService projectService;

    public SubProjectController(SubProjectService subProjectService, ProjectService projectService) {
        this.subProjectService = subProjectService;
        this.projectService = projectService;
    }

    @GetMapping("/create_subProjectForm/{projectId}")
    public String createSubProjectForm(Model model, @PathVariable int projectId) {

        ProjectModel project = projectService.getProjectById(projectId);
        SubProjectModel subproject = new SubProjectModel();

        model.addAttribute("subProject", subproject);
        model.addAttribute("projectBudget", project.getBudget());
        return "create_subProjectForm";
    }

    @PostMapping("/create_subProject")
    public String createSubProject(@RequestParam int projectId,
                                   @ModelAttribute("subProject") SubProjectModel subProject,
                                   Model model) {

        ProjectModel project = projectService.getProjectById(projectId);
        double projectBudget = project.getBudget();

        // Hvis subproject budget er større end project budget, send en fejl
        if (subProject.getBudget() > projectBudget) {
            model.addAttribute("errorMessage", "Subproject budget cannot exceed project budget!");
            model.addAttribute("subProject", subProject);
            model.addAttribute("projectBudget", projectBudget);
            return "create_subProjectForm";
        }

        // Hvis budgettet er ok, opret subproject
        subProjectService.createSubproject(projectId, subProject);
        return "redirect:/get_subprojects/" + projectId;
    }

    @GetMapping("/get_subprojects/{projectId}")
    public String getSubProjects(@PathVariable int projectId, Model model) {

        // SubProjectModel subProjectModel = new SubProjectModel();
        List<SubProjectModel> allSubprojects = subProjectService.getSubProjects(projectId);

       // model.addAttribute("subProject",subProjectModel);
        model.addAttribute("allSubprojects",allSubprojects);

        return "get_subprojects";

    }

    @PostMapping("/subProject_done/{subprojectId}")
    public String subProjectDone(@PathVariable int subprojectId, @RequestParam("subProjectId") int subProjectId) {

        subProjectService.markSubprojetAsDone(subProjectId);

        return "redirect:/get_subprojects/" + subprojectId;

    }

    @PostMapping("/subProject_not_done/{subprojectId}")
    public String subProjectNotDone(@PathVariable int subprojectId, @RequestParam("subProjectId") int subProjectId) {

        subProjectService.markSubprojctAsNotDone(subProjectId);

        return "redirect:/get_subprojects/" + subprojectId;
    }

}
