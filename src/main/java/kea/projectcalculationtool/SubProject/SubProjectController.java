package kea.projectcalculationtool.SubProject;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class SubProjectController {

    SubProjectService subProjectService;

    public SubProjectController(SubProjectService subProjectService) {
        this.subProjectService = subProjectService;
    }

    @GetMapping("/create_subProjectForm/{projectId}")
    public String createSubProjectForm(Model model, @PathVariable int projectId) {
        model.addAttribute("subProject",new SubProjectModel());
        return "create_subProjectForm";
    }
    @PostMapping("/create_subProject")
    public String createSubProject(@RequestParam int projectId,
                                   @ModelAttribute("subProject") SubProjectModel subProjectModel) {
        subProjectService.createSubproject(projectId, subProjectModel);
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
