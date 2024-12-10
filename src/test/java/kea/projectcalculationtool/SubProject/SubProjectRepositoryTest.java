package kea.projectcalculationtool.SubProject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(true)
//@ActiveProfiles("h2")
class SubProjectRepositoryTest {

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        //clean up database after each test
        jdbcTemplate.execute("DELETE FROM sub_project");
    }

    @Test
    void createSubproject() {
        //Arrange
        int projectId = 1;
        SubProjectModel subProject = new SubProjectModel(0,projectId, "John", LocalDate.of(2020,01,01),LocalDate.of(2021,02,02),1000,"testing", false);

        //Act
        subProjectRepository.createSubproject(1,subProject);

        //Assert
        List<SubProjectModel> subProjects = subProjectRepository.getSubprojectsByProjectId(subProject.getProjectId());
        subProjects.add(subProject);
        assertTrue(subProjects.contains(subProject),"subproject added to database");

    }

    @Test
    void getSubprojectsByProjectId() {
        //Arrange
        int subProjectId = 0;
        //Act

        List<SubProjectModel> subProjects = subProjectRepository.getSubprojectsByProjectId(subProjectId);

        //Assert
        assertNotNull(subProjects, "subprojects is null");
    }

    @Test
    void markASubprojectAsDone() {
        // Arrange
        int projectId = 0;
        SubProjectModel subPro1 = new SubProjectModel(1, projectId, "Test Subproject",
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 12), 10000, "Test Description", false);

        // Act
        subProjectRepository.markASubprojectAsDone(subPro1.getSubProjectId());

        // Assert

        SubProjectModel updatedSubPro = subProjectRepository.getSubprojectById(subPro1.getSubProjectId());
        assertTrue(updatedSubPro.isStatus(), "subproject should be marked as done");
    }

    @Test
    void markASubprojectAsNotDone() {
        // Arrange
        int projectId = 0;
        SubProjectModel subPro1 = new SubProjectModel(1, projectId, "test subproject",
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 12), 10000, "Test Description", true);

        // Act
        subProjectRepository.markASubprojectAsNotDone(subPro1.getSubProjectId());

        // Assert

        SubProjectModel updatedSubPro = subProjectRepository.getSubprojectById(subPro1.getSubProjectId());
        assertFalse(updatedSubPro.isStatus(), "Subproject should be marked as not done");
    }
}