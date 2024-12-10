package kea.projectcalculationtool.SubProject;

import kea.projectcalculationtool.Project.ProjectModel;
import kea.projectcalculationtool.Project.ProjectService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@WebMvcTest(SubProjectController.class)
public class SubProjectControllerTest {

    //@Autowired annotationen injekter MockMvc. MockMvc er en spring komponent som udfører en HTTP request test uden at starte en server
    @Autowired
    private MockMvc mockMvc;

    //@mockbean er en kunstig implementering af en klasse. den ersatter afhængighed med en dummy-version.
    @MockBean
    private SubProjectService subProjectService;

    @MockBean
    private ProjectService projectService;

    // test kørt af JUNIT test runner.. tester en successfuld oprettelse af subprojekt
    @Test
    public void testCreateSubProject_Success() throws Exception {
        int projectId = 1;
        SubProjectModel subProject = new SubProjectModel(0, projectId, "SubProject A", LocalDate.now(), LocalDate.now().plusDays(10), 500.0, "Description", false);
        ProjectModel project = new ProjectModel();
        project.setBudget(1000.0);

        //konfigurere den mockede projektservice til at returnere den forudbestemte project når getProjectById(projektId) metoden er kaldt
        Mockito.when(projectService.getProjectById(projectId)).thenReturn(project);

        //eksekvere en HTTP POST request til url endpoint "/create_subProject"
        mockMvc.perform(MockMvcRequestBuilders.post("/create_subProject")
                        .param("projectId", String.valueOf(projectId)) //tilføjer request parameter
                        .flashAttr("subProject", subProject)) //simulere en midlertidlig oprettelse af subprojekt. .flashatt gemmer data i sessionen mellem http anmodning og redirect.
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) //varificere respons status kode indikere en redirect i 3xx serien.
                .andExpect(MockMvcResultMatchers.redirectedUrl("/get_subprojects/" + projectId)); //varificere at redirect URl er korrekt

        Mockito.verify(subProjectService).createSubproject(projectId, subProject); // tjekker om service metoden bliver kaldt korrekt på mocken og at controlleren har modtaget og fortolket inputdataen korrekt, anvendt forretningslogikken fra service og returneret det korrekte svar
    }

    @Test
    public void testCreateSubProject_BudgetExceeds() throws Exception {
        int projectId = 1;
        SubProjectModel subProject = new SubProjectModel(0, projectId, "SubProject B", LocalDate.now(), LocalDate.now().plusDays(10), 1500.0, "Description", false);
        ProjectModel project = new ProjectModel();
        project.setBudget(1000.0);

        Mockito.when(projectService.getProjectById(projectId)).thenReturn(project);

        mockMvc.perform(MockMvcRequestBuilders.post("/create_subProject")
                        .param("projectId", String.valueOf(projectId))
                        .flashAttr("subProject", subProject))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/create_subProjectForm/" + projectId))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("errorMessage"));
    }
}