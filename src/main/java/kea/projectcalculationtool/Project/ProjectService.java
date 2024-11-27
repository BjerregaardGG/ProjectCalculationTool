package kea.projectcalculationtool.Project;

import org.springframework.stereotype.Service;

@Service
public class ProjectService {


    ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    public double calculateTime(int projectId) {
        return projectRepository.calculateTime(projectId);
    }
}
