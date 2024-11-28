package kea.projectcalculationtool.Project;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProjectRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //Made a rowmapper so when SELECT is used, it ensures that all the values are added to a projectModel
    private final RowMapper<ProjectModel> projectModelRowMapper = (rs, rowNum) ->
            new ProjectModel(
        rs.getString("name"),
        rs.getDate("start_date").toLocalDate(),
        rs.getDate("deadline").toLocalDate(),
        rs.getDouble("budget"),
        rs.getString("description"),
        rs.getBoolean("status"));

    //Will give you a list of all projects, using the projectModelRowMapper
    public List<ProjectModel> getAllProjects() {
        String sql = "SELECT name, start_date, deadline, budget, description, status FROM project";
        return jdbcTemplate.query(sql, projectModelRowMapper);
    }
    //Will insert the new project into the database.
    public void createProject(ProjectModel project) {
        String sql = "INSERT INTO project(name, start_date, deadline, budget, description, status ) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                project.getProjectName(),
                project.getStartDate(),
                project.getDeadline(),
                project.getBudget(),
                project.getProjectDescription(),
                project.getStatus());

    }
    //Ensures that all tasks duration is added together when they belong to a certain project/projectId
    public double calculateTime(int projectId) {
        //using a inner query, which first gives us the subproject id that is bound to the project id, and then the tasks that are bound to these subprojects.
        String sql = "SELECT SUM(duration) FROM task WHERE sub_project_id = (SELECT sub_project_id FROM sub_project WHERE project_id = ?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{projectId}, Double.class);
    }
}
