package kea.projectcalculationtool.Employee;

import kea.projectcalculationtool.Task.TaskModel;
import kea.projectcalculationtool.Task.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest

@ActiveProfiles("h2")

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
        "classpath:db/schema.sql",
        "classpath:db/data.sql"
})

public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllEmployees() {
        List<EmployeeModel> employees = employeeRepository.getAllEmployees();
        assertNotNull(employees);
    }

    @Test
    void getEmployeesByProjectMinusTask(){
        int projectId = 1;
        List<EmployeeModel> employees = employeeRepository.getEmployeeByProjectMinusTask(projectId);
        assertNotNull(employees);
    }

    @Test
    void getEmployeesByTaskId(){
        int taskId = 1;
        List<EmployeeModel> employees = employeeRepository.getEmployeesByTaskID(taskId);
        assertNotNull(employees);
    }

    @Test
    void addEmployeeToTask(){
        int taskId = 3;
        int employeeId = 5;
        employeeRepository.addEmployeeToTask(taskId, employeeId);

        EmployeeModel employee = employeeRepository.getEmployeesByTaskID(taskId).get(0);
        TaskModel task = taskRepository.getTask(taskId);
        assertNotNull(employee);
        assertNotNull(task);

    }

}
