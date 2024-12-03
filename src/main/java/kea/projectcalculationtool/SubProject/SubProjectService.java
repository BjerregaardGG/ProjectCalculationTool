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

    public List<SubProjectModel> getAllSubProjects() {
        return subProjectRepository.getAllSubProjects();
    }
}