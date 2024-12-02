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
}
