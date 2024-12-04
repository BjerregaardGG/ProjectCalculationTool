package kea.projectcalculationtool.Employee;

import kea.projectcalculationtool.Project.ProjectRepository;
import kea.projectcalculationtool.Project.ProjectService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
        mockMvc.perform(post("/create_employee"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("create_employee"));
    }






}
