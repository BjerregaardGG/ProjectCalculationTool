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

    public List<SubProjectModel> getSubprojectsByProjectId(int projectId){

        String sql = "SELECT * FROM sub_project WHERE project_id = ?";

        return jdbcTemplate.query(sql, projectModelRowMapper, projectId);
    }

    public void markASubprojectAsDone(int id){

        String query = "UPDATE sub_project SET status = ? WHERE id = ?";

        jdbcTemplate.update(query, true, id);

    }

    public void markASubprojectAsNotDone(int id){

        String query = "UPDATE sub_project SET status = ? WHERE id = ?";

        jdbcTemplate.update(query, false, id);
    }
    public SubProjectModel getSubprojectById(int id){
        String sql = "SELECT * FROM sub_project WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, projectModelRowMapper, id);
    }

}