package kea.projectcalculationtool.SubProject;

import java.time.LocalDate;

public class SubProjectModel {

    private int subProjectId;
    private int projectId;
    private String subProjectName;
    private String subProjectDescription;
    private double budget;
    private LocalDate startDate;
    private LocalDate deadline;
    private boolean status;

    public SubProjectModel(int subProjectId, int projectId, String subProjectName, LocalDate startDate, LocalDate deadline, double budget, String subProjectDescription, boolean status) {
        this.subProjectId = subProjectId;
        this.projectId = projectId;
        this.subProjectName = subProjectName;
        this.startDate = startDate;
        this.deadline = deadline;
        this.budget = budget;
        this.subProjectDescription = subProjectDescription;
        this.status = status;
    }

    public int getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(int subProjectId) {
        this.subProjectId = subProjectId;
    }
    public SubProjectModel(){}

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public String getSubProjectDescription() {
        return subProjectDescription;
    }

    public void setSubProjectDescription(String subProjectDescription) {
        this.subProjectDescription = subProjectDescription;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}