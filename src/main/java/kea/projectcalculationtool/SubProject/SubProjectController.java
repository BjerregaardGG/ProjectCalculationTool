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
        return "redirect:/home";
    }

}
