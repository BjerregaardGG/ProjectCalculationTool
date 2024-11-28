package kea.projectcalculationtool.Project;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public double calculateTime(int projectId) {
        return projectRepository.calculateTime(projectId);
    }

    public void createProject(ProjectModel project) {
        projectRepository.createProject(project);
    }

    public List<ProjectModel> getAllProjects() {
        return projectRepository.getAllProjects();
    }

    public List<ProjectModel> getActiveProjects() {
        return projectRepository.getActiveProjects();
    }
}