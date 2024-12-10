package kea.projectcalculationtool.SubProject;

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

    public void updateSubproject(SubProjectModel subProject){
        subProjectRepository.updateSubproject(subProject);
    }
    public SubProjectModel getSubProjectById(int subProjectId){
        return subProjectRepository.getSubprojectById(subProjectId);
    }
}