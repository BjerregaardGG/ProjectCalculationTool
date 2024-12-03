package kea.projectcalculationtool.SubProject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubProjectRepository {
    JdbcTemplate jdbcTemplate;

    public SubProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private final RowMapper<SubProjectModel> projectModelRowMapper = (rs, rowNum) ->
            new SubProjectModel(
                    rs.getInt("id"),
                    rs.getInt("project_id"),
                    rs.getString("name"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getDate("deadline").toLocalDate(),
                    rs.getDouble("budget"),
                    rs.getString("description"),
                    rs.getBoolean("status"));

    public void createSubproject(int projectId, SubProjectModel subProject){
        String sql = "INSERT INTO sub_project(project_id, name, start_date, deadline, budget, description, status) VALUES(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                projectId,
                subProject.getSubProjectName(),
                subProject.getStartDate(),
                subProject.getDeadline(),
                subProject.getBudget(),
                subProject.getSubProjectDescription(),
                subProject.isStatus());
    }

    public List<SubProjectModel> getAllSubProjects() {
        String sql = "SELECT * FROM sub_project";
        return jdbcTemplate.query(sql, projectModelRowMapper);
    }
}