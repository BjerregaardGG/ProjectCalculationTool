package kea.projectcalculationtool.Task;

import kea.projectcalculationtool.SubProject.SubProjectModel;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTaskAndAddEmployee(TaskModel task, int taskId, int employeeId) {
        try {
            taskRepository.createTask(task, taskId, employeeId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<TaskModel> getAllTasksBasedOnSubProjectId(int subProjectId) {
        return taskRepository.getAllTasksBySubProjectId(subProjectId);
    }

    public List<TaskModel> getTasksSortedByPriority(int subProjectId) {
        return taskRepository.getTasksSortedByPriority(subProjectId);
    }

    public TaskModel getTask(int id) {
        return taskRepository.getTask(id);
    }

    public void markTaskAsDone(int id){
        taskRepository.markATaskAsDone(id);
    }

    public void markTaskAsNotDone(int id){
        taskRepository.markATaskAsNotDone(id);
    }

    public void updateTask(TaskModel taskModel){
        taskRepository.updateTask(taskModel);
    }

    public TaskModel getTaskById(int taskId){
        return taskRepository.getTaskById(taskId);
    }
    public int getProjectIdBySubProjectId(int subProjectId) {
       return taskRepository.getProjectIdBySubProjectId(subProjectId);
    }


}
