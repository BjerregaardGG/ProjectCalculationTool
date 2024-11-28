package kea.projectcalculationtool.Task;

import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

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

    public TaskModel getTask(int id) {
        return taskRepository.getTask(id);
    }

}
