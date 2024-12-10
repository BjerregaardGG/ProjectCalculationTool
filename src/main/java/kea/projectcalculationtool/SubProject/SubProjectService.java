package kea.projectcalculationtool.SubProject;

import kea.projectcalculationtool.Project.ProjectModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubProjectService {

    SubProjectRepository subProjectRepository;

    public SubProjectService(SubProjectRepository subProjectRepository) {
        this.subProjectRepository = subProjectRepository;
    }
    public void createSubproject(int projectId, SubProjectModel subProject){
            subProjectRepository.createSubproject(projectId, subProject);

        }

    public List<SubProjectModel> getSubProjects(int projectId){

        return subProjectRepository.getSubprojectsByProjectId(projectId);
    }

    public void markSubprojetAsDone(int id){
        subProjectRepository.markASubprojectAsDone(id);
    }

    public void markSubprojctAsNotDone(int id){
        subProjectRepository.markASubprojectAsNotDone(id);
    }

    public boolean canAdd(double budget, double budget2) {
        return budget <= budget2;
    }
    public SubProjectModel getSubprojectById(int id){
        return subProjectRepository.getSubprojectById(id);
    }
}


