package course.courseWork.service;


import course.courseWork.exceptions.CategoryNotFoundException;
import course.courseWork.exceptions.TaskNotFoundException;
import course.courseWork.model.Category;
import course.courseWork.model.Task;
import course.courseWork.model.Users;
import course.courseWork.repository.CategoryRepository;
import course.courseWork.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final TaskRepository taskRepository;

    @Override
    public Object addCategoryByUser(Category category, Users users) {
        Optional<Category> categories = categoryRepository
                .getCategoryByCategoryIdAndUsers(category.getCategoryId(), category.getUsers());
        if (categories.isEmpty()) {
            category.setUsers(users);
            return categoryRepository.save(category);
        } else {
            return "Категория с таким названием уже существует";
        }
    }

    @Override
    public List<Category> getAllCategoriesByUser(Users users) {
        return categoryRepository.getAllCategoriesByUsers(users);
    }

    @Override
    public Optional<Category> getCategoryByIdAndUser(Long categoryId, Users users) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository
                .findCategoryByCategoryIdAndUsers(categoryId, users);

        if (category.isPresent() && category.get().getUsers().getUserId().equals(users.getUserId())) {
            return category;
        } else {
            throw new CategoryNotFoundException();
        }
    }

    @Override
    public void deleteCategoryByCategoryIdAndUsers(Category category, Users users) throws CategoryNotFoundException {
        Optional<Category> categoryToDelete = categoryRepository
                .findCategoryByCategoryIdAndUsers(category.getCategoryId(), users);
        if (categoryToDelete.isPresent() && categoryToDelete.get().getUsers().getUserId().equals(users.getUserId())) {
            categoryToDelete.get().setUsers(null);
            categoryRepository.delete(categoryToDelete.get()); // Не срабатывает
        } else {
            throw new CategoryNotFoundException();
        }
    }

    @Override
    public void updateCategoryByCategoryIdAndUsers(Long categoryId, Category updateCategory, Users users) throws CategoryNotFoundException {
        Optional<Category> optionalCategory = categoryRepository
                .findCategoryByCategoryIdAndUsers(categoryId, users);
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException();
        } else {
            if (optionalCategory.get().getUsers().getUserId().equals(users.getUserId())) {
                Category categoryToUpdate = optionalCategory.get();
                categoryToUpdate.setCategoryName(updateCategory.getCategoryName());
                categoryRepository.save(categoryToUpdate);
            }
        }
    }

    @Override
    public Optional<Category> addTaskToCategory(
            Category category, Users users, Long taskId) throws TaskNotFoundException {
        Optional<Task> task = taskRepository.findByTaskIdAndUsers(taskId, users);
        if (task.isPresent()) {
            category.getTasks().add(task.get());
            return Optional.of(categoryRepository.save(category));
        } else {
            throw new TaskNotFoundException();
        }
    }

    @Override
    public Optional<Category> removeTaskFromCategory(
            Category category, Users users, Long taskId) throws TaskNotFoundException {
        Optional<Task> task = taskRepository.findByTaskIdAndUsers(taskId, users);
        if (task.isPresent()) {
            category.getTasks().remove(task.get());
            return Optional.of(categoryRepository.save(category));
        } else {
            throw new TaskNotFoundException();
        }
    }
}
