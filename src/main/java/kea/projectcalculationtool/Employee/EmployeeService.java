package kea.projectcalculationtool.Employee;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void createEmployee(EmployeeModel employeeModel) {
        employeeRepository.createEmployee(employeeModel);
    }

    public boolean findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    public boolean findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public EmployeeModel findEmployee(String username, String password) {
        return employeeRepository.findEmployee(username, password);
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

    public void addEmployeeToTask(int taskId, int employeeId) {
        employeeRepository.addEmployeeToTask(taskId, employeeId);
    }

}
