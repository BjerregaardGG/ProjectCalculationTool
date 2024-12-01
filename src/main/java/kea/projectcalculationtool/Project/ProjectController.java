package kea.projectcalculationtool.Project;

import kea.projectcalculationtool.Employee.EmployeeModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<EmployeeModel> employees = projectService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "create_project";
    }

    @PostMapping("/create_project")
    public String createNewProject(@ModelAttribute ("project") ProjectModel project,
                                   @RequestParam("employees") List<Integer> employees, Model model) {
        List<ProjectModel> projects = projectService.getAllProjects();
        // checks if the name exist in the projects
        for(ProjectModel projectModel : projects){
            if(project.getProjectName().equals(projectModel.getProjectName())){
                System.out.println("Name Already exist," + project.getProjectName());
                model.addAttribute("Error", true);
                return "create_project";
            }
        }
        projectService.createProject(project);
        for(Integer employee : employees){
            projectService.addEmployeeToProject(employee,project.getProjectId());
        }

        return "redirect:/home";
    }

    // will get a List of employees and Projects to choose from, and values from those will be combinded to add to project_team
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
        projectService.addEmployeeToProject(employeeId,projectId);
        return "redirect:/home";
    }

    @GetMapping("/project/{projectId}/time")
    public String showProjectTime(@PathVariable int projectId,Model model) {
        double totalTime = projectService.calculateTime(projectId);
        model.addAttribute("totalTime", totalTime);
        return "some-project-page";
    }

    @GetMapping("/activeProjects")
    public String getActiveProjects(Model model){
        List<ProjectModel> activeProjects = projectService.getActiveProjects();
        model.addAttribute("projects", activeProjects);
        return "activeProjects";
    }


    @GetMapping("/project/{projectId}/cost")
    public String showProjectCost(@PathVariable int projectId,Model model) {
        double totalTime = projectService.calculateTime(projectId);
        List<EmployeeModel> employee = projectService.getAllEmployeesInTask(projectId);
        double newTime = totalTime / employee.size();
        double sum = 0;
        //Calculate total price based on job and time used.
        for(EmployeeModel employeeModel : employee){
            EmployeeModel.Roles roles = employeeModel.getRoles();
            sum += roles.getWage() * newTime;
        }
        model.addAttribute("totalPrice", sum);
        return "some-project-page";
    }
}
