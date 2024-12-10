package kea.projectcalculationtool.Project;

import kea.projectcalculationtool.Employee.EmployeeModel;
import kea.projectcalculationtool.Employee.EmployeeRepository;
import kea.projectcalculationtool.Task.TaskModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.util.TestContextSpringFactoriesUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(true)
@ActiveProfiles("h2")
class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    void getAllProjects() {
        int expected = 7;
        List<ProjectModel> actual = projectRepository.getAllProjects();
        assertEquals(expected,actual.size());
    }

    @Test
    void createProject() {
        ProjectModel project = new ProjectModel(500, "John",LocalDate.of(2020,01,01), LocalDate.of(2021, 01,01), 1.1,
        "pretty good", false,10);
        ProjectModel actual = projectRepository.createProject(project);
        assertNotNull(actual);
        assertEquals(8, projectRepository.getAllProjects().size());

    }

    @Test
    void calculateTime() {
        double expected = 18;
        double actual = projectRepository.calculateTime(1);
        assertEquals(expected, actual);
    }

    @Test
    void getAllEmployeesInTask() {
        int expected = 2;
        List<EmployeeModel> employeesInTask = projectRepository.getAllEmployeesInTask(3);
        assertEquals(expected, employeesInTask.size());
    }

    @Test
    void getTaskId(){
        int expected = 3;
        int UsedProjectId =1;
        List<Integer> taskids = projectRepository.getTaskId(UsedProjectId);
        assertEquals(expected, taskids.size());
    }


    @Test
    void getTaskFromid(){
          int TestDuration = 6;
          boolean TestStatus = false;
          TaskModel acutal = projectRepository.getTaskFromId(1);
          assertNotNull(acutal);
          assertEquals(TestDuration, acutal.getDuration());
          assertEquals(TestStatus, acutal.getTaskStatus());
    }
    @Test
    void getProjectIdFromEmployeeID() {
        int Expected = 1;
        int testEmployeeId =3;
        int actual = projectRepository.getProjectIdFromEmployeeID(testEmployeeId);
        assertEquals(Expected,actual);

    }

    @Test
    void getAllEmployees() {
        int expected = 15;
        List<EmployeeModel> employees = projectRepository.getAllEmployees();
        assertEquals(expected, employees.size());
    }

    @Test
    void addEmployeeToProject() {
        int TestEmployeeId = 7;
        int TestProjectId = 1;
        projectRepository.addEmployeeToProject(TestEmployeeId, TestProjectId);

        String sql = "SELECT project_id FROM project_team WHERE employee_id =?";
        Object object = jdbcTemplate.queryForObject(sql, Object.class, TestEmployeeId);
        assertEquals(TestProjectId, object);
    }

    @Test
    void getActiveProjects() {
        int expected = 7;
        List<ProjectModel> actual = projectRepository.getActiveProjects();
        assertEquals(expected, actual.size());
    }

    @Test
    void getEmployeesFromProjectTeam() {
        int expected = 6;
        List<Integer> actual = projectRepository.getEmployeesFromProjectTeam();
        assertEquals(expected, actual.size());
    }

    @Test
    void updateProjectStatus() {
        int testProjectId = 1;
        boolean testProjectStatus = true;
        projectRepository.updateProjectStatus(testProjectId, testProjectStatus);
        String Sql ="SELECT status FROM project WHERE id=? ";
        Object object = jdbcTemplate.queryForObject(Sql, Object.class, testProjectId);
        assertEquals(testProjectStatus, object);
    }
}