package kea.projectcalculationtool.Project;

import java.util.Date;

public class ProjectModel {
    private String name;
    private Date startDate;
    private Date endDate;
    private double budget;
    private String description;
    private Boolean isDone;

    public ProjectModel(String name, Date startDate, Date endDate, double Budget, String description) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = Budget;
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public double getBudget() {
        return budget;
    }
    public void setBudget(double budget) {
        budget = budget;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Boolean getDone() {
        return isDone;
    }


}
