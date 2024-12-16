package kea.projectcalculationtool.SubProject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

@ActiveProfiles("h2")

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
        "classpath:db/schema.sql",
        "classpath:db/data.sql"
})
class SubProjectRepositoryTest {

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Test
    void createSubproject() {
        //Arrange
        int projectId = 1;
        String subProjectName = "John";
        SubProjectModel subProject = new SubProjectModel(0,projectId, "John", LocalDate.of(2020,01,01),LocalDate.of(2021,02,02),1000,"testing", false);

        //Act
        subProjectRepository.createSubproject(projectId,subProject);

        //Assert
        List<SubProjectModel> subProjects = subProjectRepository.getSubprojectsByProjectId(projectId);
        boolean isSubProjectAdded = false;
        for (SubProjectModel sp : subProjects) {
            if (subProjectName.equals(sp.getSubProjectName())) {
                isSubProjectAdded = true;
                break;
            }
        }

        assertTrue(isSubProjectAdded, "Subproject with the name 'John' should be added to the database.");
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
        SubProjectModel subPro1 = subProjectRepository.getSubprojectById(4);
        subPro1.setStatus(false);
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