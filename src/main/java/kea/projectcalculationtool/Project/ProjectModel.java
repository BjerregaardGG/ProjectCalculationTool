package kea.projectcalculationtool.Project;

import java.util.Date;

public class ProjectModel {
    private String projectName;
    private String projectDescription;
    private double Budget;
    private Date startDate;
    private Date Deadline;

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

    public ProjectModel(String projectName, String projectDescription, double Budget, Date startDate, Date Deadline) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.Budget = Budget;
        this.startDate = startDate;
        this.Deadline = Deadline;
    }
}
