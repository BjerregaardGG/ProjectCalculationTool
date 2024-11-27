package kea.projectcalculationtool.Project;

import java.util.Date;

public class ProjectModel {
    private String name;
    private Date startDate;
    private Date endDate;
    private double Budget;
    private String description;

    public ProjectModel(String name, Date startDate, Date endDate, double Budget, String description) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.Budget = Budget;
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
        return Budget;
    }
    public void setBudget(double budget) {
        Budget = budget;
    }
    public String getDescription() {
        return description;
    }

}
