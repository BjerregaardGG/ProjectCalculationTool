package kea.projectcalculationtool.Task;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest

@ActiveProfiles("h2")

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
        "classpath:db/schema.sql",
        "classpath:db/data.sql"
})

public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTask(){
        int taskId = 1;
        TaskModel task = taskRepository.getTask(taskId);
        assertNotNull(task);
    }

    @Test
    void getTasksBySubprojectId() {
        int subprojectId = 1;
        List<TaskModel> tasks = taskRepository.getAllTasksBySubProjectId(subprojectId);
        assertNotNull(tasks);
    }

    @Test
    void getTasksSortedByPriority(){
        int priority = 5;
        List<TaskModel> tasks = taskRepository.getTasksSortedByPriority(priority);
        assertNotNull(tasks);
    }

    @Test
    void createTask(){
        int subprojectId = 1;
        int employeeId = 1;
        TaskModel task = new TaskModel();
        taskRepository.createTask(task, subprojectId, employeeId);
        assertNotNull(task.getTaskId(), "Task ID should not be null after creation");

        TaskModel newTask = taskRepository.getTask(task.getTaskId());
        assertNotNull(newTask);
        assertEquals(task.getTaskId(), newTask.getTaskId());
        assertEquals(task.getTaskName(), newTask.getTaskName());
    }





}
