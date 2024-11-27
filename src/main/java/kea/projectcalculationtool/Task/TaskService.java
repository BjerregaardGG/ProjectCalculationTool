package kea.projectcalculationtool.Task;

import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask(TaskModel task, int id) {
        try {
            taskRepository.createTask(task, id);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TaskModel getTask(int id) {
        return taskRepository.getTask(id);
    }

}
