package kea.projectcalculationtool.Project;

import kea.projectcalculationtool.Employee.EmployeeModel;
import kea.projectcalculationtool.Employee.EmployeeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {
  private final JdbcTemplate jdbcTemplate;
  private final EmployeeRepository employeeRepository;

  public ProjectRepository(JdbcTemplate jdbcTemplate, EmployeeRepository employeeRepository) {
    this.jdbcTemplate = jdbcTemplate;
    this.employeeRepository = employeeRepository;
  }

  //Made a rowmapper so when SELECT is used, it ensures that all the values are added to a projectModel
  private final RowMapper<ProjectModel> projectModelRowMapper = (rs, rowNum) ->
          new ProjectModel(
                  rs.getInt("id"),
                  rs.getString("name"),
                  rs.getDate("start_date").toLocalDate(),
                  rs.getDate("deadline").toLocalDate(),
                  rs.getDouble("budget"),
                  rs.getString("description"),
                  rs.getBoolean("status"),
                  rs.getInt("work_hours_per_project"));


  private final RowMapper<EmployeeModel> employeeModelRowMapper = (rs, rowNum) -> new EmployeeModel(
          rs.getInt("id"),
          rs.getString("name"),
          rs.getString("email"),
          rs.getString("username"),
          rs.getString("password"),
          EmployeeModel.Roles.valueOf(rs.getString("roles")));

  // Will give you a list of all projects, using the projectModelRowMapper
  public List<ProjectModel> getAllProjects() {
    String sql = "SELECT id, name, start_date, deadline, budget, description, status,work_hours_per_project FROM project";
    return jdbcTemplate.query(sql, projectModelRowMapper);
  }

  // Will insert the new project into the database.
  public ProjectModel createProject(ProjectModel project) {
    String sql = "INSERT INTO project(name, start_date, deadline, budget, description, status, work_hours_per_project ) VALUES (?, ?, ?, ?, ?, ?)";
    jdbcTemplate.update(sql,
            project.getProjectName(),
            project.getStartDate(),
            project.getDeadline(),
            project.getBudget(),
            project.getProjectDescription(),
            project.getStatus(),
            project.getWorkHoursPerProject());

    String select = "SELECT * FROM project WHERE name =?";
    return jdbcTemplate.queryForObject(select, projectModelRowMapper, project.getProjectName());
  }

  // Ensures that all tasks duration is added together when they belong to a
  // certain project/projectId
  public double calculateTime(int projectId) {
    // using a inner query, which first gives us the subproject id that is bound to
    // the project id, and then the tasks that are bound to these subprojects.
    String sql = "SELECT SUM(duration) FROM task WHERE sub_project_id IN (SELECT id FROM sub_project WHERE project_id = ?)";
    try {
      return jdbcTemplate.queryForObject(sql, Double.class, projectId);
    } catch (Exception e) {
      // Return 0.0 if no result is found or no durations exist
      return 0.0;
    }
  }
  public List<EmployeeModel> getAllEmployeesInTask (int taskId){
    String sql = "SELECT * FROM employee WHERE id IN (SELECT employee_id FROM task_employee WHERE task_id = ?)";
    return jdbcTemplate.query(sql, employeeModelRowMapper, taskId);
  }
  public List<Integer> getTaskId (Integer projectId){
    //String sql = "SELECT * FROM employee WHERE id = (SELECT id FROM task_employee WHERE task_id = ?) ";
    String sql = "SELECT id FROM task WHERE sub_project_id IN (SELECT id FROM sub_project WHERE project_id = ?)";
    return jdbcTemplate.queryForList(sql, Integer.class, projectId);
  }

  public Integer getProjectIdFromEmployeeID (Integer employeeID){
    String sql = "SELECT project_id FROM project_team WHERE employee_id = ?";
    try {
      return jdbcTemplate.queryForObject(sql, Integer.class, employeeID);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }
  public List<EmployeeModel> getAllEmployees () {
    String queryEmployee = "SELECT * FROM employee";
    return jdbcTemplate.query(queryEmployee, employeeModelRowMapper);
  }

  public void addEmployeeToProject ( int employeeId, int projectId) {
    String sql = "INSERT INTO project_team(employee_id, project_id) VALUES (?, ?)";
    jdbcTemplate.update(sql, employeeId, projectId);
  }

  public List<ProjectModel> getActiveProjects () {
    String sql = "select * from project WHERE status = false";
    return jdbcTemplate.query(sql, projectModelRowMapper);
  }

  public List<Integer> getEmployeesFromProjectTeam () {
    String sql = "SELECT employee_id FROM project_team";
    return jdbcTemplate.queryForList(sql, Integer.class);
  }
  // Used to complete a project
  public void updateProjectStatus (Integer projectId,boolean status){
    String statusSql = "UPDATE project SET status =? WHERE id =?";
    jdbcTemplate.update(statusSql, status, projectId);
  }
      // gives us the role of the employee.
  public EmployeeModel.Roles getRoleFromId ( int employeeId){
    String sql = "SELECT roles FROM employee WHERE id = ?";
    System.out.print(jdbcTemplate.queryForObject(sql, EmployeeModel.Roles.class, employeeId));
    return jdbcTemplate.queryForObject(sql, EmployeeModel.Roles.class, employeeId);
  }

  public double calculateCost (Integer projectId){
    try {
    // Adding two array list, so we have the id and a list of employees
      List<Integer> task_ids = getTaskId(projectId);
      System.out.println(task_ids.size());
      List<EmployeeModel> employee = new ArrayList<>();
      System.out.println(employee.size());
      double totalTime = calculateTime(projectId);
      // in this loop every task/task_id will be iterated and for each task all employees bound to it will be added to employeelist
      for (Integer task_id : task_ids) {
        employee.addAll(getAllEmployeesInTask(task_id));
      }
      System.out.println(employee.size());
      // based on the size the total time will change.
      double newTime = totalTime / employee.size();
      double sum = 0;
      // Calculate total price based on job and time used.
      for (EmployeeModel employeeModel : employee) {
        EmployeeModel.Roles roles = employeeModel.getRoles();
        sum += roles.getWage() * newTime;
      }
      return sum;
      } catch (NullPointerException e) {
        System.out.println(e.getMessage());
        return 0.0;
      }
  }
  public ProjectModel getProjectById ( int projectId){
    String sql = "SELECT * FROM project WHERE id = ?";
    return jdbcTemplate.queryForObject(sql, projectModelRowMapper, projectId);
  }
  //

  public long getTimeForProject (Integer projectId){
    ProjectModel project = getProjectById(projectId);
    long daysInAWeek = 7;
    long weekendDays = 2;
    int workHoursPerDay = project.getWorkHoursPerProject();
    int employeeCount = employeeRepository.getAllEmployeeInProject(projectId).size();
    LocalDate startDate = project.getStartDate();
    LocalDate deadline = project.getDeadline();
    long timeUsedOnTask = (long)calculateTime(projectId);
    // finds the difference in days between the start date and deadline
    long diffInDays = ChronoUnit.DAYS.between(startDate, deadline);
    System.out.println(diffInDays);
    System.out.println(diffInDays);
    // finds how many weekend days there are in the time frame between the start date and deadline
    long weekendDaysInProject = diffInDays/daysInAWeek*weekendDays;
    System.out.println(weekendDaysInProject);
    long daysInProject = diffInDays - weekendDaysInProject;
    System.out.println(daysInProject);
    long totalHoursInProject = daysInProject * workHoursPerDay;
    System.out.println(totalHoursInProject);
    long possibleWorkHoursPerDay = totalHoursInProject / daysInProject;
    System.out.println(possibleWorkHoursPerDay);
    return possibleWorkHoursPerDay-timeUsedOnTask;
  }
}