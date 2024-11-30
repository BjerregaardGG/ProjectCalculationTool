package kea.projectcalculationtool.Employee;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeModel> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    public List<EmployeeModel> getAllEmployeesByProject(int projectId) {
        return employeeRepository.getEmployeeByProjectMinusTask(projectId);
    }

    public List<EmployeeModel> getAllEmployeesByTask(int taskId) {
        return employeeRepository.getEmployeesByTaskID(taskId);
    }

}
