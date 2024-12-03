package kea.projectcalculationtool.Project;

import java.time.LocalDate;

public class ProjectModel {

    private int projectId;
    private String projectName;
    private String projectDescription;
    private double Budget;
    private LocalDate startDate;
    private LocalDate deadline;
    private boolean status;
    private int hoursPerProject;

    public ProjectModel() {
    }

    public ProjectModel(String name,LocalDate startDate,LocalDate deadline ,double budget, String description,boolean status, int hoursPerProject) {
        this.projectName = name;
        this.deadline = deadline;
        this.Budget = budget;
        this.projectDescription = description;
        this.startDate = startDate;
        this.status=status;
        this.hoursPerProject =hoursPerProject;
    }
    public ProjectModel(String projectName,String projectDescription,double Budget,LocalDate startDate,LocalDate Deadline) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.Budget = Budget;
        this.startDate = startDate;
        this.deadline = Deadline;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public double getBudget() {
        return Budget;
    }

    public void setBudget(double budget) {
        Budget = budget;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getHoursPerProject() {
        return hoursPerProject;
    }
    public void setHoursPerProject(int hoursPerProject) {
        this.hoursPerProject = hoursPerProject;
    }

}
