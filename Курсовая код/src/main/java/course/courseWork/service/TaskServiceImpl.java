package course.courseWork.service;

import course.courseWork.exceptions.TaskNotFoundException;
import course.courseWork.exceptions.TaskNotFoundExceptionByDeadline;
import course.courseWork.model.Task;
import course.courseWork.model.Users;
import course.courseWork.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;

    @Override
    public Task addTask(Task task, Users users) {
        task.setUsers(users);
        task.setDateCreate(new Date());
        if (task.getCompleted() == null) {
            task.setCompleted(false);
        }
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasksByUser(Users users) {
        return taskRepository.findAllByUsers(users);
    }

    @Override
    public Object getTasksByDeadlineAndUser(Date onDay, Users users) {
        List<Task> taskList = taskRepository.findAllByDeadlineAndUsers(onDay, users);
        if (taskList.isEmpty()) {
            return "К этому дню ничего не запланировано";
        }
        return taskList;
    }

    @Override
    public void updateTaskByIdAndUser(Task updateTask, Long taskId, Users users) throws TaskNotFoundException {
        Optional<Task> optionalTask = taskRepository.findByTaskIdAndUsers(taskId, users);
        if (optionalTask.isPresent() && users.getUserId().equals(optionalTask.get().getUsers().getUserId())) {
            Task task = optionalTask.get();
            task.setDeadline(updateTask.getDeadline());
            if (updateTask.getCompleted() != null) {
                task.setCompleted(updateTask.getCompleted());
            }
            task.setDescription(updateTask.getDescription());
            task.setTitle(updateTask.getTitle());
            task.setUpdateDate(new Date());
            taskRepository.save(task);
            log.info("task saved {}", taskRepository.findByTaskIdAndUsers(taskId, users));
        } else {
            throw new TaskNotFoundException();
        }
    }

    @Override
    public void deleteTaskByIdAndUser(Long taskId, Users users) throws TaskNotFoundException {
        Optional<Task> optionalTask = taskRepository.findByTaskIdAndUsers(taskId, users);
        if (optionalTask.isPresent() && users.getUserId().equals(optionalTask.get().getUsers().getUserId())) {
            Task task = optionalTask.get();
            task.setUsers(null);
            taskRepository.delete(task); // Не срабатывает :(
        } else {
            throw new TaskNotFoundException();
        }
    }

    @Override
    public List<Task> getTasksByDeadlineAndUserAndSortByImportance(
            Date deadline, Users users, boolean sort) throws TaskNotFoundExceptionByDeadline {
        List<Task> taskList = taskRepository.findAllByDeadlineAndUsers(deadline, users);
        if (taskList.isEmpty()) {
            throw new TaskNotFoundExceptionByDeadline(deadline);
        }
        if (!sort) {
            taskList.sort(Comparator.comparing(Task::getImportance));
        } else {
            taskList.sort(Comparator.comparing(Task::getImportance).reversed());
        }
        return taskList;
    }

    @Override
    public List<Task> getTasksByImportanceAndUser(int importance, Users users) {
        return taskRepository.findAllByImportanceAndUsers(importance, users);
    }


    @Override
    public List<Task> getAllTaskByUserAndSortByImportance(Users users, boolean sort) {
        List<Task> taskList = taskRepository.findAllByUsers(users);
        if (!sort) {
            taskList.sort(Comparator.comparing(Task::getImportance));
        } else {
            taskList.sort(Comparator.comparing(Task::getImportance).reversed());
        }
        return taskList;
    }

    @Override
    public List<Task> getAllTaskByUserAndSortByDeadline(Users users, boolean sort) {
        List<Task> taskList = taskRepository.findAllByUsers(users);
        if (!sort) {
            taskList.sort(Comparator.comparing(Task::getDeadline));
        } else {
            taskList.sort(Comparator.comparing(Task::getDeadline).reversed());
        }
        return taskList;
    }

}