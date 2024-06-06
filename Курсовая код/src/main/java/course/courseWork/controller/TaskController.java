package course.courseWork.controller;

import course.courseWork.exceptions.TaskNotFoundException;
import course.courseWork.exceptions.TaskNotFoundExceptionByDeadline;
import course.courseWork.model.Task;
import course.courseWork.model.Users;
import course.courseWork.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Object> addTask(@RequestBody Task task, @AuthenticationPrincipal Users users) {
        if (task.getImportance() >= 10 || task.getImportance() <= 0) {
            Task newTask = taskService.addTask(task, users);
            return ResponseEntity.ok(newTask);
        } else {
            return ResponseEntity.badRequest().body("Важность от 0 до 10");
        }
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasksByUser(@AuthenticationPrincipal Users users) {
        List<Task> tasks = taskService.getAllTasksByUser(users);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/deadline")
    public ResponseEntity<Object> getTasksByDeadlineAndUser(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date deadline, @AuthenticationPrincipal Users users) throws TaskNotFoundExceptionByDeadline {
        Object result = taskService.getTasksByDeadlineAndUser(deadline, users);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update")
    public ResponseEntity<Task> updateTaskByTitleAndUser(
            @RequestParam Long taskId, @RequestBody Task updateTask, @AuthenticationPrincipal Users users) {
        try {
            taskService.updateTaskByIdAndUser(updateTask, taskId, users);
            updateTask.setTaskId(updateTask.getTaskId());
            return ResponseEntity.ok(updateTask);
        } catch (TaskNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTaskByTitleAndUser(
            @RequestParam Long taskId,
            @AuthenticationPrincipal Users users) {
        try {
            taskService.deleteTaskByIdAndUser(taskId, users);
            return ResponseEntity.ok("Task deleted successfully!");
        } catch (TaskNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/importance")
    public ResponseEntity<List<Task>> getTasksByImportanceAndUser(
            @RequestParam int importance, @AuthenticationPrincipal Users users) {
        List<Task> tasks = taskService.getTasksByImportanceAndUser(importance, users);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/sortByImportance")
    public ResponseEntity<List<Task>> getAllTaskByUserAndSortByImportance(
            @RequestParam boolean sort, @AuthenticationPrincipal Users users) {
        List<Task> taskList = taskService.getAllTaskByUserAndSortByImportance(users, sort);
        return ResponseEntity.ok(taskList);
    }

    @GetMapping("/sortByDeadline")
    public ResponseEntity<List<Task>> getAllTaskByUserAndSortByDeadline(
            @RequestParam boolean sort, @AuthenticationPrincipal Users users) {
        List<Task> taskList = taskService.getAllTaskByUserAndSortByDeadline(users, sort);
        return ResponseEntity.ok(taskList);
    }

    @GetMapping("/getByDeadlineAndSortByImportance")
    public ResponseEntity<List<Task>> getAllTaskByUserAndSortByImportance(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date deadline,
            @RequestParam boolean sort, @AuthenticationPrincipal Users users) throws TaskNotFoundExceptionByDeadline {
        List<Task> tasks = taskService.getTasksByDeadlineAndUserAndSortByImportance(deadline, users, sort);
        return ResponseEntity.ok(tasks);
    }
}
