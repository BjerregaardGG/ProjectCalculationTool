package kea.projectcalculationtool.Project;

import jakarta.servlet.http.HttpSession;
import kea.projectcalculationtool.Employee.EmployeeModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    model.addAttribute("employees", projectService.getAllEmployees());
    model.addAttribute("EmpOnProjects", projectService.getEmployeesFromProjectTeam());
    return "create_project";
  }

  @PostMapping("/create_project")
  public String createNewProject(@ModelAttribute("project") ProjectModel project,
      @RequestParam("employees") List<Integer> employees, RedirectAttributes redirectAttributes) {
    try {
      projectService.createProject(project, employees);
      return "redirect:/home";
    }catch(Exception e){
      redirectAttributes.addFlashAttribute("Error", "true");
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
  @GetMapping("/addToProject")
  public String addToProject(Model model, HttpSession session) {
    Integer EmployeeId = (Integer) session.getAttribute("employeeId");
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
  public String getActiveProjects(Model model,HttpSession session) {
    Integer EmployeeID = (Integer) session.getAttribute("employeeID");
    if(EmployeeID == null){
      return "redirect:/login";
    }
    List<ProjectModel> activeProjects = projectService.getActiveProjects();
    model.addAttribute("projects", activeProjects);
    model.addAttribute("ProjectIdFromEmployeeId", projectService.getProjectIdFromEmployeeID(EmployeeID));
    model.addAttribute("Manager", EmployeeModel.Roles.MANAGER);
    model.addAttribute("role", projectService.getRoleFromId((EmployeeID)));
    return "activeProjects";
  }
  @GetMapping("/done_project/{projectid}")
  public String doneProject(@PathVariable int projectid, Model model) {

    return "done_project";
  }

  @PostMapping("/updateProjectStatus")
  public String updateProjectStatus(@RequestParam("projectId") Integer projectid) {
    projectService.updateProjectStatus(projectid, true);

    return "redirect:/activeProjects";
  }
}
