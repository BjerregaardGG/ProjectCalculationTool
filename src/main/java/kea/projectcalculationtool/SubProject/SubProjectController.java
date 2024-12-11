package kea.projectcalculationtool.SubProject;

import jakarta.servlet.http.HttpSession;
import kea.projectcalculationtool.Employee.EmployeeModel;
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
    public String getSubProjects(@PathVariable int projectId, Model model, HttpSession session) {
        Integer EmployeeId = (Integer) session.getAttribute("employeeID");
        List<SubProjectModel> allSubprojects = subProjectService.getSubProjects(projectId);

        model.addAttribute("allSubprojects",allSubprojects);
        model.addAttribute("role", projectService.getRoleFromId((EmployeeId)));
        model.addAttribute("Manager", EmployeeModel.Roles.MANAGER);

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
    @GetMapping("/updatesubproject/{subProjectId}")
    public String updateSubProjectForm(@PathVariable("subProjectId") Integer subProjectId, Model model, HttpSession session) {
        Integer EmployeeID = (Integer) session.getAttribute("employeeID");
        if(EmployeeID == null){
            return "redirect:/login";
        }
        if(subProjectId == null){
            System.out.println("projectId is null");
            return "redirect:/activeProjects";
        }

        SubProjectModel subProject = subProjectService.getSubProjectById(subProjectId);
        int projectId = subProject.getProjectId();
        System.out.println("projectId is " + projectId);
        System.out.println(subProject.getSubProjectName());
        model.addAttribute("subproject", subProject);
        model.addAttribute("role", projectService.getRoleFromId((EmployeeID)));
        model.addAttribute("Manager", EmployeeModel.Roles.MANAGER);

        return "updatesubproject";
    }
    @PostMapping("/updatesubproject/{subProjectId}")
    public String submitUpdateSubProject(@ModelAttribute SubProjectModel subProject) {
        subProjectService.updateSubproject(subProject);
        //todo find projekt id for dette subprojekt og returner det i variabel "projectId"
        int projectId = subProject.getProjectId();
        System.out.println("projectId is " + projectId);
        return "redirect:/get_subprojects/" + projectId;
    }
}
