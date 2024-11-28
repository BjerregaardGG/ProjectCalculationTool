package kea.projectcalculationtool.Task;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // rowmapper --> creates task object out of a resultSet
    private final RowMapper<TaskModel> taskModelRowMapper = ((rs, rowNum) ->
            new TaskModel(
            rs.getString("name"), rs.getString("description"),
            rs.getDate("start_date").toLocalDate(),
            rs.getDate("deadline").toLocalDate(),
            rs.getInt("duration")
    ));

    // method for creating a task
    public void createTask(TaskModel task, int subProjectId, int employeeId) {

        String query = "insert into task (name, start_date, deadline, duration, description, sub_project_id) values (?, ?, ?, ?, ?, ?)";

        int rowsAffected = jdbcTemplate.update(
                query,
                task.getTaskName(),
                task.getTaskStartDate(),
                task.getTaskDeadline(),
                task.getDuration(),
                task.getTaskDescription(),
                subProjectId
        );

        if(rowsAffected != 1 ) {
            System.out.println("Problem. Could not insert the new task");
        }

        // last_insert_id() --> because we need the id from the current form to insert in TaskEmployee
        String employeeAssignment = "insert into task_employee (task_id, employee_id) values (last_insert_id(), ?)";

        // connects employee with the current task in TaskEmployee
        jdbcTemplate.update(employeeAssignment, employeeId);

    }

    // method for getting a task by id
    public TaskModel getTask(int id) {

        String query = "select * from task where id = ?";

        return jdbcTemplate.queryForObject(query, taskModelRowMapper, id);
    }

    // add employee to task


}
