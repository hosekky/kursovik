package course.courseWork.service;

import course.courseWork.exceptions.CategoryNotFoundException;
import course.courseWork.exceptions.TaskNotFoundException;
import course.courseWork.model.Category;
import course.courseWork.model.Users;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Object addCategoryByUser(Category category, Users users);

    List<Category> getAllCategoriesByUser(Users users);

    Optional<Category> getCategoryByIdAndUser(Long categoryId, Users users) throws CategoryNotFoundException;

    void deleteCategoryByCategoryIdAndUsers(Category category, Users users) throws CategoryNotFoundException;

    void updateCategoryByCategoryIdAndUsers(Long categoryId, Category category, Users users) throws CategoryNotFoundException;

    Optional<Category> addTaskToCategory(
            Category category, Users users, Long categoryId) throws TaskNotFoundException;

    Optional<Category> removeTaskFromCategory(
            Category category, Users users, Long categoryId) throws TaskNotFoundException;
}
