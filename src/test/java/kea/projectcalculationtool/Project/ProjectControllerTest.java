package kea.projectcalculationtool.Project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createProject() throws Exception {
        mockMvc.perform(get("/create_project"))
                .andExpect(status().isOk())
                .andExpect(view().name("create_project"));
    }

    @Test
    void createNewProject() throws Exception {
        mockMvc.perform(post("/create_project")
                        // When using post method and it has modelattribute or requestparam, you need to add the parameters
                        .param("projectName", "Test Project") // For ProjectModel
                        .param("employees", "1", "2", "3") // Employees list as integers
                        // This will check what type the data is / comming from.
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED) // Required for @ModelAttribute
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home")); // Verify the redirection
    }





    @Test
    void addToProject()throws Exception {
        mockMvc.perform(get("/addToProject")
                        .param("employeeId", "1")
                        .param("projectId", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        )
                .andExpect(status().isOk())
                .andExpect(view().name("add_to_project"));
    }


    @Test
    void assignToProject() throws Exception {
        mockMvc.perform(post("/addToProject"))

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("/redirect:/home"));
    }

    @Test
    void showProjectTime() {
    }

    @Test
    void showProjectCost() {
    }
}