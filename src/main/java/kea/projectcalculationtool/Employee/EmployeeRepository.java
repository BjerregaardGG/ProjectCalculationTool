package kea.projectcalculationtool.Employee;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    public void createEmployee(EmployeeModel employee) {
        String queryEmployee = "INSERT INTO employee(name, email, username, password, roles) VALUES (?,?,?,?,?)";

        int employeeCreated = jdbcTemplate.update(
            queryEmployee,
            employee.getFullName(),
            employee.getEmail(),
            employee.getUsername(),
            employee.getPassword(),
            employee.getRoles().name()
        );
        if (employeeCreated != 1) {
            throw new RuntimeException("Employee creation failed");
        }
    }

    public boolean findByUsername(String username) {
            String queryEmployee = "SELECT COUNT(*) FROM employee WHERE username = ?";
            Integer count = jdbcTemplate.queryForObject(queryEmployee, Integer.class, username);
            return count > 0;

    }

    public boolean findByEmail(String email) {
        String queryEmployee = "SELECT COUNT(*) FROM employee WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(queryEmployee, Integer.class, email);
        return count > 0;
    }

    public EmployeeModel findEmployee (String username, String password) {
        String queryEmployee = "SELECT * FROM employee WHERE username = ? AND password = ?";
        return jdbcTemplate.queryForObject(queryEmployee, (rs, rownum )-> {
            EmployeeModel employee = new EmployeeModel();
            employee.setEmployeeID(rs.getInt("id"));
            employee.setFullName(rs.getString("name"));
            employee.setEmail(rs.getString("email"));
            employee.setUsername(rs.getString("username"));
            employee.setPassword(rs.getString("password"));
            return employee;
        }, username, password);
    }

    // method to get all employees
    public List<EmployeeModel> getAllEmployees() {

        String query = "select * from employee";

        return jdbcTemplate.query(query, EmployeeModelRowmapper);

    }

    // Method to get employees based on a project and who's not assigned a task
    public List<EmployeeModel> getEmployeeByProjectMinusTask(int projectId) {

        // sql to get an employee on a specific project but with no tasks
        String query = "select e.id, e.name, e.email from Employee e " +
                "join project_team pt on e.id = pt.employee_id " + // join to find all employees on a project
                "left join task_employee te ON e.id = te.employee_id " + // left join to find all employees with and without tasks
                "where pt.project_id = ? and te.task_id is null"; // finds employees for project with no tasks

        return jdbcTemplate.query(query,EmployeeModelRowmapper, projectId);

    }

    // Method for getting an employee based on a task
    public List <EmployeeModel> getEmployeesByTaskID(int taskID) {

        String query = "select e.id, e.name, e.email from Employee e " +
                "join task_employee on e.id = task_employee.employee_id " + // maps employees to task based on id
                "where task_employee.task_id = ?";

        return jdbcTemplate.query(query,EmployeeModelRowmapper, taskID);

    }

    public void addEmployeeToTask(int taskId, int employeeId) {

        String query = "insert into task_employee (employee_id, task_id) values (?, ?)";

        jdbcTemplate.update(query, employeeId, taskId);

    }



}


