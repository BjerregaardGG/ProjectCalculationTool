package kea.projectcalculationtool.Employee;

import org.springframework.stereotype.Service;

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
}
