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

    public TaskService(TaskRepository taskRepository,EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    // creates a task and adds an employee - logs errors at the same time
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

    // get tasks by priority and deadline - saves the objects in a map
    public Map<String, Object> getTaskSortedByPriority(int subProjectId) {
        Map<String, Object> map = new HashMap<>();

        try {
            // gets all tasks by subprojectId (1)
            List<TaskModel> priorityTasks = taskRepository.getTasksSortedByPriority(subProjectId);
            // map to save employee by task (2)
            Map<Integer, List<EmployeeModel>> employeesByTask = new HashMap<>();

            for (TaskModel task : priorityTasks) {
                try {
                    List<EmployeeModel> employees = employeeRepository.getEmployeesByTaskID(task.getTaskId());
                    employeesByTask.put(task.getTaskId(), employees);
                } catch (Exception e) {
                    System.out.println("Error getting employee by task id" + task.getTaskId());
                }
            }

            int totalHours = 0; // total hours for a subproject
            int employees = 0; // total employees for a task
            int hoursPrEmployee = 0; // average hours an employee has to work

            for (TaskModel task : priorityTasks) {
                if (!task.getTaskStatus()) { // if task is not done...
                    totalHours += task.getDuration(); // number of hours on active projects (3)

                    try {
                        List<EmployeeModel> employeesByTasks = employeeRepository.getEmployeesByTaskID(task.getTaskId());
                        employees += employeesByTasks.size(); // number of employees on active projects
                    } catch (Exception e) {
                        System.out.println("Error getting employee by task id" + task.getTaskId());
                    }
                }
            }

            if (employees > 0) {
                try {
                    hoursPrEmployee = totalHours / employees; // average workload pr employee
                } catch (ArithmeticException e) {
                    System.out.println("error trying to calculate average workload - division by 0?");
                    hoursPrEmployee = 0;
                }
            }

            // the map that is being returned and can be used in the controller
            map.put("totalHours", totalHours);
            map.put("priorityTasks", priorityTasks);
            map.put("employeesByTask", employeesByTask);
            map.put("hoursPrEmployee", hoursPrEmployee);

        } catch (Exception e) {
            System.out.println("Error in getting task by priority" + subProjectId);
        }

            return map;
        }

    // method for deleting an employee
    public void deleteEmployeeFromTask(int taskId) {

        try {
            taskRepository.deleteEmployeeFromTask(taskId);

        }catch (DataAccessException e) {
            System.out.println("Error occurred while deleting task" + e.getMessage());
        }
    }

    public void markTaskAsDone(int id){

        try {
            taskRepository.markATaskAsDone(id);

        }catch (DataAccessException e) {
            System.out.println("Error occurred while marking task" + e.getMessage());
        }
    }

    public void markTaskAsNotDone(int id){

        try {
            taskRepository.markATaskAsNotDone(id);
        }catch (DataAccessException e) {
            System.out.println("Error occurred while marking task" + e.getMessage());
        }
    }

    // get task by id
    public TaskModel getTask(int taskId) {

        try {
            return taskRepository.getTask(taskId);

        }catch (DataAccessException e) {
            System.out.println("Error occurred while getting task" + e.getMessage());
        }
        return null;
    }

}
