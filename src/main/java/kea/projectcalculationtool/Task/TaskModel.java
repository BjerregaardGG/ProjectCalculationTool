package kea.projectcalculationtool.Task;

import org.springframework.scheduling.config.Task;

import java.sql.Date;
import java.time.LocalDate;

public class TaskModel {
    private Integer taskId;
    private Integer subProjectId;
    private String taskName;
    private String taskDescription;
    private LocalDate taskStartDate;
    private LocalDate taskDeadline;
    private boolean taskStatus;
    private int duration;
    private int priority;

    public TaskModel(Integer taskId, String taskName, String taskDescription, LocalDate taskStartDate, LocalDate taskDeadline, boolean taskStatus, int duration) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStartDate = taskStartDate;
        this.taskDeadline = taskDeadline;
        this.taskStatus = taskStatus;
        this.duration = duration;
    }

    public TaskModel(Integer taskId, String taskName, String taskDescription, LocalDate taskStartDate, LocalDate taskDeadline, int duration, int priority, boolean taskStatus, int subProjectId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStartDate = taskStartDate;
        this.taskDeadline = taskDeadline;
        this.duration = duration;
        this.priority = priority;
        this.taskStatus = taskStatus;
        this.subProjectId = subProjectId;
    }

    public TaskModel(){

    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public int getPriority(){
        return priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
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

    public boolean getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSubProjectId() {
        return subProjectId;
    }
    public void setSubProjectId(int subProjectId) {
        this.subProjectId = subProjectId;
    }
}
