package kea.projectcalculationtool.Task;

import org.springframework.scheduling.config.Task;

import java.sql.Date;
import java.time.LocalDate;

public class TaskModel {
    private String taskName;
    private String taskDescription;
    private LocalDate taskStartDate;
    private LocalDate taskDeadline;
    private String taskStatus;
    private int duration;

    public TaskModel(String taskName, String taskDescription, LocalDate taskStartDate, LocalDate taskDeadline, String taskStatus, int duration) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStartDate = taskStartDate;
        this.taskDeadline = taskDeadline;
        this.taskStatus = taskStatus;
        this.duration = duration;
    }

    public TaskModel(String taskName, String taskDescription, LocalDate taskStartDate, LocalDate taskDeadline, int duration) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStartDate = taskStartDate;
        this.taskDeadline = taskDeadline;
        this.duration = duration;
    }

    public TaskModel(){

    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDate getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(LocalDate taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public LocalDate getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(LocalDate taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
