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

    // rowmapper --> skaber task objekter ud fra ResultSet
    private final RowMapper<TaskModel> taskModelRowMapper = ((rs, rowNum) ->
            new TaskModel(
            rs.getString("name"), rs.getString("description"),
            rs.getDate("startDate").toLocalDate(),
            rs.getDate("deadline").toLocalDate(),
            rs.getInt("duration")
    ));

    // method for creating a task
    public void createTask(TaskModel task, int subProjectId) {

        String query = "insert into Task (name, startDate, deadline, duration, description, subProjectId) values (?, ?, ?, ?, ?, ?)";

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
    }

    // method for getting a task by id
    public TaskModel getTask(int id) {

        String query = "select * from Task where taskId = ?";

        return jdbcTemplate.queryForObject(query, taskModelRowMapper, id);
    }

    // add employee to task


}
