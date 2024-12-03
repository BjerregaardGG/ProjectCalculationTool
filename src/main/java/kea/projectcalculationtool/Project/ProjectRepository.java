package kea.projectcalculationtool.Project;

import kea.projectcalculationtool.Employee.EmployeeModel;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {
  private final JdbcTemplate jdbcTemplate;

  public ProjectRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  // Made a rowmapper so when SELECT is used, it ensures that all the values are
  // added to a projectModel
  private final RowMapper<ProjectModel> projectModelRowMapper = (rs, rowNum) -> new ProjectModel(
      rs.getInt("id"),
      rs.getString("name"),
      rs.getDate("start_date").toLocalDate(),
      rs.getDate("deadline").toLocalDate(),
      rs.getDouble("budget"),
      rs.getString("description"),
      rs.getBoolean("status"));

  private final RowMapper<EmployeeModel> employeeModelRowMapper = (rs, rowNum) -> new EmployeeModel(
      rs.getInt("id"),
      rs.getString("name"),
      rs.getString("email"),
      rs.getString("username"),
      rs.getString("password"),
      EmployeeModel.Roles.valueOf(rs.getString("roles")));

  // Will give you a list of all projects, using the projectModelRowMapper
  public List<ProjectModel> getAllProjects() {
    String sql = "SELECT id, name, start_date, deadline, budget, description, status FROM project";
    return jdbcTemplate.query(sql, projectModelRowMapper);
  }

  // Will insert the new project into the database.
  public ProjectModel createProject(ProjectModel project) {
    String sql = "INSERT INTO project(name, start_date, deadline, budget, description, status ) VALUES (?, ?, ?, ?, ?, ?)";
    jdbcTemplate.update(sql,
        project.getProjectName(),
        project.getStartDate(),
        project.getDeadline(),
        project.getBudget(),
        project.getProjectDescription(),
        project.getStatus());

    String select = "SELECT * FROM project WHERE name =?";
    return jdbcTemplate.queryForObject(select, projectModelRowMapper, project.getProjectName());
  }

  // Ensures that all tasks duration is added together when they belong to a
  // certain project/projectId
  public double calculateTime(int projectId) {
    // using a inner query, which first gives us the subproject id that is bound to
    // the project id, and then the tasks that are bound to these subprojects.
    String sql = "SELECT SUM(duration) FROM task WHERE sub_project_id = (SELECT sub_project_id FROM sub_project WHERE project_id = ?)";
    try {
      return jdbcTemplate.queryForObject(sql, Double.class, projectId);
    } catch (EmptyResultDataAccessException e) {
      // Return 0.0 if no result is found or no durations exist
      return 0.0;
    }
  }

  public List<EmployeeModel> getAllEmployeesInTask(int taskId) {
    String sql = "SELECT * FROM employee WHERE id = (SELECT employee_id FROM task_employee WHERE task_id = ?) ";
    return jdbcTemplate.query(sql, employeeModelRowMapper, taskId);
  }

  public Integer getProjectIdFromEmployeeID(Integer employeeID) {
    String sql = "SELECT project_id FROM project_team WHERE employee_id = ?";
    try {
      return jdbcTemplate.queryForObject(sql, Integer.class, employeeID);
    }
    catch(EmptyResultDataAccessException e) {
      return null;
    }
  }
  public List<EmployeeModel> getAllEmployees() {
    String queryEmployee = "SELECT * FROM employee";
    return jdbcTemplate.query(queryEmployee, employeeModelRowMapper);
  }

  public void addEmployeeToProject(int employeeId, int projectId) {
    String sql = "INSERT INTO project_team(employee_id, project_id) VALUES (?, ?)";
    jdbcTemplate.update(sql, employeeId, projectId);
  }

  public List<ProjectModel> getActiveProjects() {
    String sql = "select * from project WHERE status = false";
    return jdbcTemplate.query(sql, projectModelRowMapper);
  }
  public List<Integer> getEmployeesFromProjectTeam() {
    String sql = "SELECT employee_id FROM project_team";
    return jdbcTemplate.queryForList(sql, Integer.class);

  }
}
