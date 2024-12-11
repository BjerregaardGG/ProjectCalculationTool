package kea.projectcalculationtool.Project;

import jakarta.servlet.http.HttpSession;
import kea.projectcalculationtool.Employee.EmployeeModel;
import kea.projectcalculationtool.SubProject.SubProjectModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    model.addAttribute("employees", projectService.getAllEmployees());
    model.addAttribute("EmpOnProjects", projectService.getEmployeesFromProjectTeam());
    return "create_project";
  }

  @PostMapping("/create_project")
  public String createNewProject(@ModelAttribute("project") ProjectModel project,
      @RequestParam("employees") List<Integer> employees,RedirectAttributes redirectAttributes) {

    try {
      ProjectModel projectModel = projectService.createProject(project, employees);

      if(projectModel == null) {
        redirectAttributes.addFlashAttribute("TimeError", true);
        return "redirect:/create_project";
      }
      return "redirect:/home";

    }catch(Exception e){

      return "redirect:/create_project";
    }

  }

  @PostMapping("/delete/{projectId}")
  public String deleteProject(@PathVariable("projectId") Integer projectid){
    projectService.deleteProject(projectid);
    return "redirect:/home";
  }
  // will get a List of employees and Projects to choose from, and values from
  // those will be combinded to add to project_team
  @GetMapping("/addToProject/{projectId}")
  public String addToProject(@PathVariable("projectId") Integer projectId, Model model) {
    model.addAttribute("projectId", projectId);
    model.addAttribute("IdList", projectService.getEmployeesFromProjectTeam());
    model.addAttribute("employees", projectService.getAllEmployees());
    model.addAttribute("projects", projectService.getAllProjects());
    return "add_to_project";
  }

  @PostMapping("/addToProject")
  public String assignToProject(@RequestParam("employeeId") int employeeId, @RequestParam("projectId") int projectId) {
    try {
      projectService.addEmployeeToProject(employeeId,projectId);
      return "redirect:/activeProjects";
    }catch (Exception e){
      System.out.println(e.getMessage());
      return "redirect:/addToProject";
    }
  }

  @GetMapping("/project/{projectId}/time")
  public String showProjectTime(@PathVariable int projectId, Model model) {
    double totalTime = projectService.calculateTime(projectId);
    model.addAttribute("totalTime", totalTime);
    return "some-project-page";
  }

  @GetMapping("/activeProjects")
  public String getActiveProjects(Model model, HttpSession session) {
    Integer EmployeeID = (Integer) session.getAttribute("employeeID");
    if(EmployeeID == null){
      return "redirect:/login";
    }
    List<ProjectModel> activeProjects = projectService.getActiveProjects();
    model.addAttribute("projectService", projectService);
    model.addAttribute("projects", activeProjects);
    model.addAttribute("ProjectIdFromEmployeeId", projectService.getProjectIdFromEmployeeID(EmployeeID));
    model.addAttribute("Manager", EmployeeModel.Roles.MANAGER);
    model.addAttribute("role", projectService.getRoleFromId(EmployeeID));
    return "activeProjects";
  }

  @PostMapping("/updateProjectStatus")
  public String updateProjectStatus(@RequestParam("projectId") Integer projectId) {
    projectService.updateProjectStatus(projectId, true);
    return "redirect:/activeProjects";
  }

  @GetMapping("/updateproject/{projectId}")
  public String updateProjectForm(@PathVariable("projectId") Integer projectId, Model model, HttpSession session) {
    Integer EmployeeID = (Integer) session.getAttribute("employeeID");

    if(EmployeeID == null){
      return "redirect:/login";
    }
    if(projectId == null){
      System.out.println("projectId is null");
      return "redirect:/activeProjects";
    }
    ProjectModel project = projectService.getProjectById(projectId);
    if(project == null){
      System.out.println("hej");
      return "redirect:/activeProjects";
    }

    model.addAttribute("project", project);
    model.addAttribute("role", projectService.getRoleFromId((EmployeeID)));
    model.addAttribute("Manager", EmployeeModel.Roles.MANAGER);

    return "updateproject";
  }
  @PostMapping("/updateproject/{projectId}")
  public String submitUpdateProject(@ModelAttribute ProjectModel project) {
    projectService.updateProject(project);
    return "redirect:/activeProjects";
  }

}
