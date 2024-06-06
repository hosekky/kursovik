package course.courseWork.service;

import course.courseWork.model.Category;
import course.courseWork.model.Task;
import course.courseWork.repository.CategoryRepository;
import course.courseWork.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataCleanupService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void deleteRecordsWithNullUser() {
        List<Task> tasks = taskRepository.getAllByUsersIsNull();
        taskRepository.deleteAll(tasks);
        List<Category> categories = categoryRepository.findAll();
        categoryRepository.deleteAll(categories);
        log.info("Cleaned up records with null user");
    }
}

