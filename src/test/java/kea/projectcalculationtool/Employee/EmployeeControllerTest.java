package kea.projectcalculationtool.Employee;

import kea.projectcalculationtool.Project.ProjectModel;
import kea.projectcalculationtool.Project.ProjectRepository;
import kea.projectcalculationtool.Project.ProjectService;
import kea.projectcalculationtool.Task.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;
    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createEmployeeForm() throws Exception {
        mockMvc.perform(get("/create_employee"))
                .andExpect(status().isOk())
                .andExpect(view().name("create_employee"));
    }

    @Test
    void createEmployee() throws Exception {
        EmployeeModel employee = new EmployeeModel();
        employee.setUsername("Lasse");
        employee.setEmail("lasse@example.com");
        employee.setPassword("password");
        employee.setConfirmPassword("password");

        mockMvc.perform(post("/create_employee")
                        .param("username", employee.getUsername())
                        .param("email", employee.getEmail())
                        .param("password", employee.getPassword())
                        .param("confirmPassword",employee.getConfirmPassword()))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void logout() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }

    @Test
    void showHomepageNoLoginSession() throws Exception{
        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }

    @Test
    void showHomepage() throws Exception{
        int mockProjectId = 1;
        int mockEmployeeId = 1;
        List<ProjectModel> mockProjectModelList = new ArrayList<>();
        EmployeeModel.Roles mockRole = EmployeeModel.Roles.MANAGER;

        when(projectRepository.getProjectIdFromEmployeeID(mockEmployeeId)).thenReturn(mockProjectId);
        when(projectRepository.getRoleFromId(mockEmployeeId)).thenReturn(mockRole);
        when(projectRepository.getAllProjects()).thenReturn(mockProjectModelList);

        mockMvc.perform(get("/home").sessionAttr("employeeID", mockEmployeeId))
                .andExpect(status().isOk())
                .andExpect(view().name("homepage"));
    }

    @Test
    void addEmployeeToTaskForm() throws Exception {
        int projectId = 1;
        int subProjectId = 1;
        int taskId = 1;

        mockMvc.perform(get("/add_employee_form/{projectId}/{subProjectId}/{taskId}", projectId, subProjectId, taskId))
                .andExpect(status().isOk())
                .andExpect(view().name("employee_to_task_form"));
    }

    @Test
    void addEmployee() throws Exception {
        mockMvc.perform(post("/add_employee")
                .param("subProjectId", "1")
                .param("employeeId", "1")
                .param("projectId", "1")
                        .param("taskId", "1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/get_task/1/1"));
    }






}
