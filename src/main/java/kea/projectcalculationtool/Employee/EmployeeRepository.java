package kea.projectcalculationtool.Employee;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createEmployee(EmployeeModel employee) {
        String queryEmployee = "INSERT INTO employee VALUES (?,?,?,?,?)";

        int employeeCreated = jdbcTemplate.update(
            queryEmployee,
            employee.getFullName(),
            employee.getEmail(),
            employee.getUsername(),
            employee.getPassword(),
            employee.getRoles()
        );
        if (employeeCreated == 0) {
            throw new RuntimeException("Employee creation failed");
        }
    }

    public boolean findByUsername(String username) {
        String queryEmployee = "SELECT * FROM employee WHERE userName = ?";
        Integer count = jdbcTemplate.queryForObject(queryEmployee, Integer.class, username);
        return count > 0;
    }

    public boolean findByEmail(String email) {
        String queryEmployee = "SELECT * FROM employee WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(queryEmployee, Integer.class, email);
        return count > 0;
    }
}
