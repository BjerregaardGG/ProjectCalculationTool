package kea.projectcalculationtool.SubProject;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class SubProjectController {

    SubProjectService subProjectService;

    public SubProjectController(SubProjectService subProjectService) {
        this.subProjectService = subProjectService;
    }

    @GetMapping("/create_subProjectForm")
    public String createSubProjectForm(Model model) {
        model.addAttribute("subProject",new SubProjectModel());
        return "create_subProjectForm";
    }
    @PostMapping("/create_subProject")
    public String createSubProjectForm(@ModelAttribute("subProject") SubProjectModel subProjectModel) {
        subProjectService.createSubproject(subProjectModel);
        return "redirect:/";
    }

}
