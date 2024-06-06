package course.courseWork.service;

import course.courseWork.exceptions.TaskNotFoundException;
import course.courseWork.exceptions.TaskNotFoundExceptionByDeadline;
import course.courseWork.model.Task;
import course.courseWork.model.Users;

import java.util.Date;
import java.util.List;

public interface TaskService {

    Task addTask(Task task, Users users);

    List<Task> getAllTasksByUser(Users users);

    Object getTasksByDeadlineAndUser(Date deadline, Users users) throws TaskNotFoundExceptionByDeadline;

    void updateTaskByIdAndUser(
            Task updateTask, Long taskId, Users users) throws TaskNotFoundException;

    void deleteTaskByIdAndUser(Long taskId, Users users) throws TaskNotFoundException;

    List<Task> getTasksByDeadlineAndUserAndSortByImportance(Date deadline, Users users, boolean sort) throws TaskNotFoundExceptionByDeadline;

    List<Task> getTasksByImportanceAndUser(int importance, Users users);

    List<Task> getAllTaskByUserAndSortByImportance(Users users, boolean sort);

    List<Task> getAllTaskByUserAndSortByDeadline(Users users, boolean sort);
}

