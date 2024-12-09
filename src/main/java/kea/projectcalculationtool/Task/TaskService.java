package kea.projectcalculationtool.Task;

import kea.projectcalculationtool.Employee.EmployeeModel;
import kea.projectcalculationtool.Employee.EmployeeRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    public TaskService(TaskRepository taskRepository, EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    public void createTaskAndAddEmployee(TaskModel task, int taskId, int employeeId) {
        try {
            taskRepository.createTask(task, taskId, employeeId);

        } catch (DuplicateKeyException e) {
            System.out.println("Task id already exists" + e.getMessage());
            e.printStackTrace();
        } catch (DataAccessException e) {
            System.out.println("Error occurred while creating task" + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<TaskModel> getAllTasksBasedOnSubProjectId(int subProjectId) {
        return taskRepository.getAllTasksBySubProjectId(subProjectId);
    }

    public Map<String, Object> getTaskSortedByPriority(int subProjectId) {

            List<TaskModel> priorityTasks = taskRepository.getTasksSortedByPriority(subProjectId);

            Map<Integer, List<EmployeeModel>> employeesByTask = new HashMap<>();

            for (TaskModel task : priorityTasks) {
                List<EmployeeModel> employees = employeeRepository.getEmployeesByTaskID(task.getTaskId());
                employeesByTask.put(task.getTaskId(), employees);
            }

            // total hours for a subproject
            int totalHours = 0;
            // total employees for a task
            int employees = 0;
            //average hours an employee has to work
            int hoursPrEmployee = 0;

            for (TaskModel task : priorityTasks) {
                if (!task.getTaskStatus()) {
                    totalHours += task.getDuration(); // number of hours on active projects

                    List<EmployeeModel> employeesByTasks = employeeRepository.getEmployeesByTaskID(task.getTaskId());
                    employees += employeesByTasks.size(); // number of employees on active projects
                }
            }
            if (employees > 0) {
                hoursPrEmployee = totalHours / employees; // average workload
            }


            Map<String, Object> map = new HashMap<>();
            map.put("totalHours", totalHours);
            map.put("priorityTasks", priorityTasks);
            map.put("employeesByTask", employeesByTask);
            map.put("hoursPrEmployee", hoursPrEmployee);

            return map;

        }

    public void deleteEmployeeFromTask(int taskId) {

        try {
            taskRepository.deleteEmployeeFromTask(taskId);

        }catch (DataAccessException e) {
            System.out.println("Error occurred while deleting task" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void markTaskAsDone(int id){

        try {
            taskRepository.markATaskAsDone(id);

        }catch (DataAccessException e) {
            System.out.println("Error occurred while marking task" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void markTaskAsNotDone(int id){

        try {
            taskRepository.markATaskAsNotDone(id);
        }catch (DataAccessException e) {
            System.out.println("Error occurred while marking task" + e.getMessage());
            e.printStackTrace();
        }
    }

    public TaskModel getTask(int taskId) {

        try {
            return taskRepository.getTask(taskId);

        }catch (DataAccessException e) {
            System.out.println("Error occurred while getting task" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
