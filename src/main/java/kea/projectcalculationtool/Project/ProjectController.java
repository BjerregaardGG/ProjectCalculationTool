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

  private final ProjectRepository projectRepository;
  ProjectService projectService;

  public ProjectController(ProjectService projectService, ProjectRepository projectRepository) {
    this.projectService = projectService;
    this.projectRepository = projectRepository;
  }

  @GetMapping("/create_project")
  public String createProject(Model model) {
    model.addAttribute("project", new ProjectModel());
    List<EmployeeModel> employees = projectService.getAllEmployees();
    model.addAttribute("employees", employees);
    model.addAttribute("EmpOnProjects", projectService.getEmployeesFromProjectTeam());
    return "create_project";
  }

  @PostMapping("/create_project")
  public String createNewProject(@ModelAttribute("project") ProjectModel project,
      @RequestParam("employees") List<Integer> employees, Model model, RedirectAttributes redirectAttributes) {
    List<ProjectModel> projects = projectService.getAllProjects();
    // checks if the name exist in the projects
    System.out.println(employees.get(0));
    for (ProjectModel projectModel : projects) {
      if (project.getProjectName().equals(projectModel.getProjectName())) {
        System.out.println("Name Already exist," + project.getProjectName());
        redirectAttributes.addFlashAttribute("Error", true);
        return "redirect:/create_project";
      }
    }
    ProjectModel projectm = projectService.createProject(project);
    System.out.println(projectm.getProjectId());
    for (Integer employee : employees) {
      projectService.addEmployeeToProject(employee, projectm.getProjectId());
    }

    return "redirect:/home";
  }

  // will get a List of employees and Projects to choose from, and values from
  // those will be combinded to add to project_team
  @GetMapping("/addToProject")
  public String addToProject(Model model) {
    List<EmployeeModel> employees = projectService.getAllEmployees();
    List<ProjectModel> projects = projectService.getAllProjects();
    model.addAttribute("employees", employees);
    model.addAttribute("projects", projects);
    return "add_to_project";
  }

  @PostMapping("/addToProject")
  public String assignToProject(@RequestParam("employeeId") int employeeId, @RequestParam("projectId") int projectId) {
    projectService.addEmployeeToProject(employeeId, projectId);
    return "redirect:/activeProjects";
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
    Integer ProjectId = projectService.getProjectIdFromEmployeeID(EmployeeID);
    List<ProjectModel> activeProjects = projectService.getActiveProjects();
    model.addAttribute("projectrepo", projectRepository);
    model.addAttribute("projects", activeProjects);
    model.addAttribute("ProjectIdFromEmployeeId", ProjectId);
    model.addAttribute("Manager", EmployeeModel.Roles.MANAGER);
    model.addAttribute("role", projectService.getRoleFromId(EmployeeID));
    return "activeProjects";
  }
/*
  @GetMapping("/project/{projectId}/cost")
  public String showProjectCost(@PathVariable int projectId, Model model) {
    double totalTime = projectService.calculateTime(projectId);
    List<EmployeeModel> employee = projectService.getAllEmployeesInTask(projectId);
    double newTime = totalTime / employee.size();
    double sum = 0;
    // Calculate total price based on job and time used.
    for (EmployeeModel employeeModel : employee) {
      EmployeeModel.Roles roles = employeeModel.getRoles();
      sum += roles.getWage() * newTime;
    }
    model.addAttribute("totalPrice", sum);
    return "/home";
  }
*/
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
