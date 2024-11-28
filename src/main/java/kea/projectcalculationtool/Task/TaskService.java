package kea.projectcalculationtool.Task;

import org.springframework.stereotype.Service;

@Service
public class TaskService {

    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void deleteTask(String taskId) {
        taskRepository.deleteTask(taskId);
    }
}
