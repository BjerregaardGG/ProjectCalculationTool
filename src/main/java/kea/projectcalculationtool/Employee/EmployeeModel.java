package kea.projectcalculationtool.Employee;

public class EmployeeModel {
    private int employeeID;
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
    private Roles roles;

    enum Roles {
        INTERN(0),
        JUNIOR(220),
        SENIOR(320),
        MANAGER(500);

        private final int wage;

        Roles(int wage) {
            this.wage = wage;
        }
        public int getWage() {
            return wage;
        }
    }

    public EmployeeModel(int employeeID, String fullName, String email, String username, String password, String confirmPassword, Roles roles ) {
        this.employeeID = employeeID;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.roles = roles;
    }
    public EmployeeModel() {
    }
    public int getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String userName) {
        this.username = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Roles getRoles() {
        return roles;
    }
    public void setRoles(Roles roles) {
        this.roles = roles;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
