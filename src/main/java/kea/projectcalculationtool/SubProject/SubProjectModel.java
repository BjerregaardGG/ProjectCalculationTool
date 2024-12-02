package kea.projectcalculationtool.SubProject;

import java.util.Date;

public class SubProjectModel {
    private int projectId;
    private String subProjectName;
    private String subProjectDescription;
    private double Budget;
    private Date startDate;
    private Date Deadline;

    public SubProjectModel(String subProjectName, String subProjectDescription, double budget, Date startDate, Date deadline) {
        this.subProjectName = subProjectName;
        this.subProjectDescription = subProjectDescription;
        Budget = budget;
        this.startDate = startDate;
        Deadline = deadline;
    }
    public SubProjectModel(){}

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
        return Budget;
    }

    public void setBudget(double budget) {
        Budget = budget;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeadline() {
        return Deadline;
    }

    public void setDeadline(Date deadline) {
        Deadline = deadline;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
