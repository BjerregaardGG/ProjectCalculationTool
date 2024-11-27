package kea.projectcalculationtool.Project;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ProjectRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public double calculateTime(int projectId) {
        String sql = "SELECT SUM(duration) FROM task WHERE subproject_id = (SELECT subproject_id FROM subproject WHERE project_id = ?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{projectId}, Double.class);
    }


}
