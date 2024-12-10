package kea.projectcalculationtool.SubProject;

import kea.projectcalculationtool.Project.ProjectModel;
import kea.projectcalculationtool.Project.ProjectService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String createSubProjectForm(Model model, @PathVariable int projectId, RedirectAttributes redirectAttributes) {

        ProjectModel project = projectService.getProjectById(projectId);
        SubProjectModel subproject = new SubProjectModel();

        model.addAttribute("subProject", subproject);
        model.addAttribute("projectBudget", project.getBudget());

        // redirectAttributes only occurs after a redirect
        redirectAttributes.addFlashAttribute("errorMessage", "Subproject budget cannot exceed project budget!");
        return "create_subProjectForm";
    }

    @PostMapping("/create_subProject")
    public String createSubProject(@RequestParam int projectId,
                                   @ModelAttribute("subProject") SubProjectModel subProject,
                                   RedirectAttributes redirectAttributes) {

        ProjectModel project = projectService.getProjectById(projectId);
        double projectBudget = project.getBudget();

        // if the subproject budget is bigger than project budget, then redirect
        if (subProject.getBudget() > projectBudget) {
            redirectAttributes.addFlashAttribute("errorMessage", "Subproject budget cannot exceed project budget!");
            return "redirect:/create_subProjectForm/" + projectId;
        }

        // if the budget is lower than the project budget, then create subproject
        subProjectService.createSubproject(projectId, subProject);
        return "redirect:/get_subprojects/" + projectId;
    }

    @GetMapping("/get_subprojects/{projectId}")
    public String getSubProjects(@PathVariable int projectId, Model model) {

        List<SubProjectModel> allSubprojects = subProjectService.getSubProjects(projectId);

        ProjectModel project = projectService.getProjectById(projectId);

        model.addAttribute("allSubprojects",allSubprojects);

        model.addAttribute("project",project);

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

    @PostMapping("/deleteSubproject/{subprojectId}")
    public String deleteSubproject(@PathVariable int subprojectId, @RequestParam("projectId") int projectId) {
        subProjectService.deleteSubproject(subprojectId);
        return "redirect:/get_subprojects/" + projectId;
    }
}
