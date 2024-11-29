package kea.projectcalculationtool.Employee;

import kea.projectcalculationtool.SubProject.SubProjectModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // rowmapper --> creates Employee objects out of a resultSet
    private final RowMapper<EmployeeModel> EmployeeModelRowmapper = ((rs, rowNum) ->
            new EmployeeModel(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email")
            ));

    // method to get all employees
    public List<EmployeeModel> getAllEmployees() {

        String query = "select * from employee";

        return jdbcTemplate.query(query, EmployeeModelRowmapper);

    }

    // Method to get employees based on a project
    public List<EmployeeModel> getEmployeeByProject(int projectId) {

        // to get all information about an employee we use a join
        String query = "select e.id, e.name, e.email from Employee e " +
                "join project_team on e.id = project_team.employee_id " + // maps employees to project based on id
                "where project_team.project_id = ?";

        return jdbcTemplate.query(query,EmployeeModelRowmapper, projectId);

    }



}
