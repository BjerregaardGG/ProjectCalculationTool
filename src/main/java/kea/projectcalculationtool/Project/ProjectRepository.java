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
    public final RowMapper<ProjectModel> projectModelRowMapper = (rs, rowNum) -> new ProjectModel(
            rs.getString("name"),
            rs.getDate("startDate"),
            rs.getDate("endDate"),
            rs.getDouble("budget"),
            rs.getString("description")
    );



    public List<ProjectModel> getActiveProjects(){
        String sql = "select * from project WHERE isDone = false";
        return jdbcTemplate.query(sql, projectModelRowMapper);
    }
}
