package kea.projectcalculationtool.SubProject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SubProjectRepository {
    JdbcTemplate jdbcTemplate;

    public SubProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void createSubproject(SubProjectModel subProject){
        String sql = "INSERT INTO sub_project(project_id, name, start_date, deadline, budget, description, status) VALUES(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                subProject.getProjectId(),
                subProject.getSubProjectName(),
                subProject.getStartDate(),
                subProject.getDeadline(),
                subProject.getBudget(),
                subProject.getSubProjectDescription());
    }
}
