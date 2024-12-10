package kea.projectcalculationtool.Project;

import kea.projectcalculationtool.Employee.EmployeeModel;
import kea.projectcalculationtool.Employee.EmployeeRepository;
import kea.projectcalculationtool.Task.TaskModel;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ProjectService {

    ProjectRepository projectRepository;
    EmployeeRepository employeeRepository;

    public ProjectService(ProjectRepository projectRepository, EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }

    public double calculateTime(int projectId) {
        return projectRepository.calculateTime(projectId);
    }

  public void createProject(ProjectModel project,List<Integer> employees) {
    List<ProjectModel> projects = projectRepository.getAllProjects();
    // checks if the name exist in the projects
    for (ProjectModel projectModel : projects) {
      if (project.getProjectName().equals(projectModel.getProjectName())) {
        System.out.println("Name Already exist," + project.getProjectName());
      }
    }
    ProjectModel projectm = projectRepository.createProject(project);
    for (Integer employee : employees) {
      projectRepository.addEmployeeToProject(employee, projectm.getProjectId());
    }
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

    public void addEmployeeToProject(int employeeId,int projectId) {
        projectRepository.addEmployeeToProject(employeeId,projectId);
    }

    public List<ProjectModel> getActiveProjects() {
        return projectRepository.getActiveProjects();
    }

    public ProjectModel getProjectById(int projectId) {
        return projectRepository.getProjectById(projectId);
    }

    public double getTimeForProject(int projectId) {
        return getTimeForProject(projectId);
    }

  public List<Integer> getEmployeesFromProjectTeam() {
    return projectRepository.getEmployeesFromProjectTeam();
  }

  public void updateProjectStatus(Integer projectid, boolean status) {
    projectRepository.updateProjectStatus(projectid, status);
  }
  public void updateProject (ProjectModel project) {
      projectRepository.updateProject(project);
  }

  public Integer getProjectIdFromEmployeeID(Integer employeeID) {
    return projectRepository.getProjectIdFromEmployeeID(employeeID);
  }

  public EmployeeModel.Roles getRoleFromId(Integer employeeID) {
    return projectRepository.getRoleFromId(employeeID);
  }

  public void deleteProject(Integer projectId) {
    // Deletes everything connect to the project and the task bound to it.
    projectRepository.deleteProjectTeam(projectId);
    projectRepository.deleteProject(projectId);
    projectRepository.deleteSubProject(projectId);
    //Get all ids from task connected to project.
    List<Integer> taskIds = projectRepository.getTaskId(projectId);
    //uses task ids to delete from Task employee table and task table.
    for (Integer taskId : taskIds) {
      projectRepository.deleteFromTaskEmployee(taskId);
      projectRepository.deleteTask(taskId);
    }
  }

  public double getTaskTime(Integer task_id){
    TaskModel task = projectRepository.getTaskFromId(task_id);

    if(task != null){
      return task.getDuration();
    }
    else {
      return 0.0;
    }
  }

  // this method was sponsored by tutor alexander and chatgpt based on own original work.
  public double calculateCost(Integer projectId) {
    try {
      List<Integer> task_ids = projectRepository.getTaskId(projectId);
      double totalCost = 0.0;

      // Iterate over each task
      for (Integer task_id : task_ids) {
        double taskTime = getTaskTime(task_id); // Method to get the time for a task
        List<EmployeeModel> employeeList = getAllEmployeesInTask(task_id);

        if (employeeList.isEmpty()) {
          continue; // Skip tasks with no assigned employees
        }

        double timePerEmployee = taskTime / employeeList.size();

        // Calculate cost for each employee assigned to the task
        for (EmployeeModel employeeModel : employeeList) {
          EmployeeModel.Roles roles = employeeModel.getRoles();
          totalCost += roles.getWage() * timePerEmployee;
        }
      }
      return totalCost;

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return 0.0;
    }
  }

  public double daysLeftInProject (int projectId){
    ProjectModel project = projectRepository.getProjectById(projectId);

    long daysInAWeek = 7;
    long weekendDays = 2;
    long differenceInDays;

    LocalDate currentDate = LocalDate.now();
    LocalDate startDate = project.getStartDate();
    LocalDate deadline = project.getDeadline();

    if(currentDate.isAfter(startDate)){
      differenceInDays = ChronoUnit.DAYS.between(currentDate, deadline);
    } else {
      differenceInDays = ChronoUnit.DAYS.between(startDate, deadline);
    }

    double weekendDaysInProject = ((double) differenceInDays / daysInAWeek) * weekendDays;
    double daysInProject = differenceInDays - weekendDaysInProject;

    if(daysInProject <= 0){
      return -1;
    }

    return daysInProject;
  }

  public double getTimeForProject (Integer projectId){

    double employeeCount = employeeRepository.getAllEmployeeInProject(projectId).size();
    double taskTimeLeft = projectRepository.getTimeFromTaskNotDone(projectId);
    double daysInAWeek = daysLeftInProject(projectId);

    double hoursPerDayPerEmployee = taskTimeLeft / (employeeCount * daysInAWeek);

    return Math.ceil(hoursPerDayPerEmployee);
  }
}