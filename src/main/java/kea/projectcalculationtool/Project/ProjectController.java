package kea.projectcalculationtool.Project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProjectController {

    @GetMapping("/project/{projectId}/time")
    public String showProjectTime(@PathVariable int projectId,Model model) {
        double totalTime = projectRepository.calculateTime(projectId);

        model.addAttribute("totalTime", totalTime);
        return "some-project-page";
    }
}
