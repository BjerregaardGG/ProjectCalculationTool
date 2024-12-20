package kea.projectcalculationtool.Project;

import kea.projectcalculationtool.Employee.EmployeeModel;
import kea.projectcalculationtool.Task.TaskController;
import kea.projectcalculationtool.Employee.EmployeeRepository;
import kea.projectcalculationtool.Task.TaskModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

  public ProjectModel createProject(ProjectModel project, List<Integer> employees) {
    List<ProjectModel> projects = projectRepository.getAllProjects();
    // checks if the name exist in the projects
    for (ProjectModel projectModel : projects) {
      if (project.getProjectName().equals(projectModel.getProjectName())) {
        System.out.println("Name Already exist," + project.getProjectName());
        return null;
      }
    }
    if (project.getStartDate().isAfter(project.getDeadline())) {
      return null;
    }

    ProjectModel projectm = projectRepository.createProject(project);

    for (Integer employee : employees) {
      projectRepository.addEmployeeToProject(employee, projectm.getProjectId());
    }
    return projectm;
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

  public ProjectModel getProjectById(int projectId) {
    return projectRepository.getProjectById(projectId);
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
    // Get all ids from task connected to project.
    List<Integer> taskIds = projectRepository.getTaskId(projectId);
    // uses task ids to delete from Task employee table and task table.
    for (Integer taskId : taskIds) {
      projectRepository.deleteFromTaskEmployee(taskId);
      projectRepository.deleteTask(taskId);
    }
  }

  public void deleteTask(int taskId) {
    projectRepository.deleteFromTaskEmployee(taskId);
    projectRepository.deleteTask(taskId);
  }

  public void deleteFromTaskEmployee(int taskId) {
    projectRepository.deleteFromTaskEmployee(taskId);
  }

  public double getTaskTime(Integer task_id) {
    TaskModel task = projectRepository.getTaskFromId(task_id);

    if (task != null) {
      return task.getDuration();
    } else {
      return 0.0;
    }
  }

  // this method was sponsored by tutor alexander and chatgpt based on own
  // original work.
  public double calculateCost(Integer projectId) {
    try {
      List<Integer> task_ids = projectRepository.getTaskId(projectId);
      double totalCost = 0.0;

      // Use the same static set

      for (Integer task_id : task_ids) {
        double taskTime = getTaskTime(task_id);

        // Find employees for this specific task
        List<EmployeeModel> employeeList = new ArrayList<>();
        // Uses the Hashset from TaskController to go through the string that was stored with task id and employee id
        for (String taskEmployeeCombination : TaskController.historicalTaskEmployees) {
          //split the string into parts in a String array so task id and employee id can be accessed
          String[] parts = taskEmployeeCombination.split("-");
          //if the array is 2 long and the first index spot 0, is the same as the task id that being iterated
          //the employee will be added to the employee list, that used to calculate cost
          if (parts.length == 2 && Integer.parseInt(parts[0]) == task_id) {
            EmployeeModel employee = projectRepository.getEmployeeFromEmployeeId(Integer.parseInt(parts[1]));
            if(employee == null){
              continue;
            }
            employeeList.add(employee);
          }
        }
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