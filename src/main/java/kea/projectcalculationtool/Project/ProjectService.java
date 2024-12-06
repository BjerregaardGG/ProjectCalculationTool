package kea.projectcalculationtool.Project;

import kea.projectcalculationtool.Employee.EmployeeModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

  ProjectRepository projectRepository;

  public ProjectService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  public double calculateTime(int projectId) {
    return projectRepository.calculateTime(projectId);
  }

  public ProjectModel createProject(ProjectModel project) {
    return projectRepository.createProject(project);
  }

  public List<ProjectModel> getAllProjects() {
    return projectRepository.getAllProjects();
  }

  public List<EmployeeModel> getAllEmployeesInTask(int taskId) {
    return projectRepository.getAllEmployeesInTask(taskId);
  }

  public List<EmployeeModel> getAllEmployees() {
    return projectRepository.getAllEmployees();
  }

  public void addEmployeeToProject(int employeeId, int projectId) {
    projectRepository.addEmployeeToProject(employeeId, projectId);
  }

  public List<ProjectModel> getActiveProjects() {
    return projectRepository.getActiveProjects();
  }

  public List<Integer> getEmployeesFromProjectTeam() {
    return projectRepository.getEmployeesFromProjectTeam();
  }

  public void updateProjectStatus(Integer projectid, boolean status) {
    projectRepository.updateProjectStatus(projectid, status);
  }

  public Integer getProjectIdFromEmployeeID(Integer employeeID) {
    return projectRepository.getProjectIdFromEmployeeID(employeeID);
  }

  public EmployeeModel.Roles getRoleFromId(Integer employeeID) {
      return projectRepository.getRoleFromId(employeeID);
  }

  public void deleteProject(Integer projectId) {
    projectRepository.deleteProject(projectId);
  }
}
