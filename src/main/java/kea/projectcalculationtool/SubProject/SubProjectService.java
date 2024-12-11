package kea.projectcalculationtool.SubProject;

import kea.projectcalculationtool.Project.ProjectModel;
import kea.projectcalculationtool.Project.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubProjectService {

    private final ProjectService projectService;
    SubProjectModel subProjectModel;

    ProjectModel projectModel;

    SubProjectRepository subProjectRepository;

    public SubProjectService(SubProjectRepository subProjectRepository, ProjectService projectService) {
        this.subProjectRepository = subProjectRepository;
        this.projectService = projectService;
    }

    public void createSubproject(int projectId, SubProjectModel subProject) {
        subProjectRepository.createSubproject(projectId, subProject);

    }

    public List<SubProjectModel> getSubProjects(int projectId) {

        return subProjectRepository.getSubprojectsByProjectId(projectId);
    }

    public void markSubprojetAsDone(int id) {
        subProjectRepository.markASubprojectAsDone(id);
    }

    public void markSubprojctAsNotDone(int id) {
        subProjectRepository.markASubprojectAsNotDone(id);
    }

    public boolean canAdd(double budget, double budget2) {
        return budget <= budget2;
    }

    public void deleteSubproject(int subprojectId) {
        //Deletes subproject first then task_employee bound to that subproject and then tasks bound to it.
        subProjectRepository.deleteSubproject(subprojectId);
        // Gives a list of task ids inside the subproject
        List<Integer> taskIds = subProjectRepository.getTaskIdFromSubprojectId(subprojectId);
        //iterates through the ids and delete with each.
        for (Integer taskId : taskIds) {
            projectService.deleteFromTaskEmployee(taskId);
            projectService.deleteTask(taskId);
        }
    }
    public SubProjectModel getSubprojectById(int id){
        return subProjectRepository.getSubprojectById(id);
    }

    public void updateSubproject(SubProjectModel subProject){
        subProjectRepository.updateSubproject(subProject);
    }

    public SubProjectModel getSubProjectById(int subProjectId){
        return subProjectRepository.getSubprojectById(subProjectId);
    }
}


