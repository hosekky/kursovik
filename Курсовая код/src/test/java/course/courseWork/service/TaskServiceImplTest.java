package course.courseWork.service;

import course.courseWork.exceptions.TaskNotFoundExceptionByDeadline;
import course.courseWork.model.Task;
import course.courseWork.model.Users;
import course.courseWork.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TaskServiceImplTest {

    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);

    private final TaskService taskService = new TaskServiceImpl(taskRepository);

    @DisplayName("add task")
    @Test
    void addTask() {
        Task task = new Task();
        Task expectedTask = new Task();
        Users user = new Users();
        expectedTask.setUsers(user);
        Mockito.when(
                        taskRepository.save(task))
                .thenReturn(expectedTask);
        assertEquals(expectedTask, taskService.addTask(task, user));
    }

    @DisplayName("get all tasks")
    @Test
    void getAllTasksByUser() {
        Users user = new Users();
        Task task1 = new Task();
        task1.setUsers(user);
        Task task2 = new Task();
        task2.setUsers(user);
        List<Task> expectedTasks = List.of(task1, task2);
        Mockito.when(taskRepository
                        .findAllByUsers(user))
                .thenReturn(expectedTasks);
        assertEquals(expectedTasks, taskService.getAllTasksByUser(user));
    }

    @DisplayName("get tasks by deadline")
    @Test
    void getTasksByDeadlineAndUser() throws TaskNotFoundExceptionByDeadline {
        Users user = new Users();
        Date date = new Date();
        Task task = new Task();
        task.setDeadline(date);
        task.setUsers(user);
        List<Task> expectedTask = List.of(task);
        Mockito.when(taskRepository.findAllByDeadlineAndUsers(date, user))
                .thenReturn(expectedTask);
        assertEquals(expectedTask, taskService.getTasksByDeadlineAndUser(date, user));
    }

    @Test
    void getTasksByDeadlineAndUserAndSortByImportance() {
    }

    @DisplayName("get tasks by importance")
    @Test
    void getTasksByImportanceAndUser() {
        Task task = new Task();
        Users user = new Users();
        task.setUsers(user);
        task.setImportance(1);
        List<Task> expectedTask = List.of(task);
        Mockito.when(taskRepository.findAllByImportanceAndUsers(1, user))
                .thenReturn(expectedTask);
        assertEquals(expectedTask, taskService.getTasksByImportanceAndUser(1, user));
    }

    @DisplayName("get all tasks by user and sort by importance")
    @Test
    void getAllTaskByUserAndSortByImportance() {
    }

    @Test
    void getAllTaskByUserAndSortByDeadline() {
    }
}