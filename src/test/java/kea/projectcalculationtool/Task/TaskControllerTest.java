package kea.projectcalculationtool.Task;


import kea.projectcalculationtool.Employee.EmployeeRepository;
import kea.projectcalculationtool.Employee.EmployeeService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void showCreateTaskForm() throws Exception {

        int projectId = 1;
        int subProjectId = 1;

        mockMvc.perform(get("/task_form/project/{projectId}/{subProjectId}", projectId, subProjectId))
                .andExpect(status().isOk())
                .andExpect(view().name("create_task"));
    }

    @Test
    void createTask() throws Exception {
        mockMvc.perform(post("/create_task")
                .param("taskName", "Task Test")
                .param("subProjectId", "1").param("projectId", "1")
                .param("employeeId", "1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/get_task/1/1"));

    }

    @Test
    void getTaskBasedOnSubProject() throws Exception {
        int projectId = 1;
        int subProjectId = 1;

        mockMvc.perform(get("/get_task/{projectId}/{subProjectId}", projectId, subProjectId))
                .andExpect(status().isOk())
                .andExpect(view().name("get_task"));
    }

    @Test
    void taskDone() throws Exception {
        int taskId = 1;

        mockMvc.perform(post("/task_done/{taskId}", taskId)
                        .param("taskId", "1")
                .param("subProjectId", "1").param("projectId", "1")
                .param("employeeId", "1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/get_task/1/1"));

    }

    @Test
    void taskNotDone() throws Exception {
        int taskId = 1;

        mockMvc.perform(post("/task_not_done/{taskId}", taskId)
                        .param("taskId", "1")
                        .param("subProjectId", "1")
                        .param("projectId", "1")
                        .param("employeeId", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/get_task/1/1"));

    }



}
