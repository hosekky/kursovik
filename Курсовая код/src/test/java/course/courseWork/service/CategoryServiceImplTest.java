package course.courseWork.service;

import course.courseWork.exceptions.CategoryNotFoundException;
import course.courseWork.exceptions.TaskNotFoundException;
import course.courseWork.model.Category;
import course.courseWork.model.Task;
import course.courseWork.model.Users;
import course.courseWork.repository.CategoryRepository;
import course.courseWork.repository.TaskRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class CategoryServiceImplTest {

    private final CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);

    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);

    private final CategoryService categoryService = new CategoryServiceImpl(categoryRepository, taskRepository);

    @DisplayName("add category")
    @Test
    void addCategoryByUser() {
        Category category = new Category();
        Users user = new Users();
        user.setUserId(1L);
        Category expectedCategory = new Category();
        expectedCategory.setCategoryId(1L);
        expectedCategory.setUsers(user);
        Mockito.when(categoryRepository.getCategoryByCategoryIdAndUsers(0L, null))
                .thenReturn(null);
        Mockito.when(categoryRepository.save(category))
                .thenReturn(expectedCategory);
        Object result = categoryService.addCategoryByUser(category, user);
        assertEquals(expectedCategory, result);
    }

    @DisplayName("get all categories")
    @Test
    void getAllCategoriesByUser() {
        Category category = new Category();
        Category category2 = new Category();
        Users user = new Users();
        user.setUserId(1L);
        category.setUsers(user);
        category2.setUsers(user);
        List<Category> expectedCategories = List.of(category, category2);
        Mockito.when(categoryRepository.getAllCategoriesByUsers(user))
                .thenReturn(expectedCategories);
        assertEquals(expectedCategories, categoryService.getAllCategoriesByUser(user));
    }

    @DisplayName("get category by id")
    @Test
    void getCategoryByIdAndUser() throws CategoryNotFoundException {
        Category category = new Category();
        Users user = new Users();
        user.setUserId(1L);
        category.setUsers(user);
        category.setCategoryId(1L);
        Category expectedCategory = new Category();
        expectedCategory.setCategoryId(1L);
        expectedCategory.setUsers(user);
        Mockito.when(categoryRepository.findCategoryByCategoryIdAndUsers(category.getCategoryId(), user))
                .thenReturn(Optional.of(expectedCategory));
        Optional<Category> optionalCategory = categoryService.getCategoryByIdAndUser(1L, user);
        assertTrue(optionalCategory.isPresent());
        assertEquals(expectedCategory, optionalCategory.get());
    }

    @Test
    void deleteCategoryByCategoryIdAndUsers() {
    }

    @Test
    void updateCategoryByCategoryIdAndUsers() {
    }

    @DisplayName("add task to category")
    @Test
    void addTaskToCategory() throws TaskNotFoundException {
        Task task = new Task();
        Category category = new Category();
        Users user = new Users();
        user.setUserId(1L);
        task.setUsers(user);
        task.setTaskId(1L);
        category.setUsers(user);
        category.setCategoryId(1L);
        category.setTasks(new ArrayList<>());
        Category expectedCategory = new Category();
        expectedCategory.setCategoryId(1L);
        expectedCategory.setUsers(user);
        expectedCategory.setTasks(new ArrayList<>());
        Mockito.when(taskRepository.findByTaskIdAndUsers(1L, user))
                .thenReturn(Optional.of(task));
        Mockito.when(categoryRepository.save(category))
                .thenReturn(expectedCategory);
        Optional<Category> optionalCategory = categoryService.addTaskToCategory(category, user, 1L);
        assertTrue(optionalCategory.isPresent());
        assertEquals(expectedCategory, optionalCategory.get());
    }

    @DisplayName("remove task from category")
    @Test
    void removeTaskFromCategory() throws TaskNotFoundException {
        Task task = new Task();
        Category category = new Category();
        Users user = new Users();
        user.setUserId(1L);
        task.setUsers(user);
        task.setTaskId(1L);
        category.setUsers(user);
        category.setCategoryId(1L);
        category.setTasks(new ArrayList<>());
        category.getTasks().add(task);
        Category expectedCategory = new Category();
        expectedCategory.setCategoryId(1L);
        expectedCategory.setUsers(user);
        expectedCategory.setTasks(new ArrayList<>());
        Mockito.when(taskRepository.findByTaskIdAndUsers(1L, user))
                .thenReturn(Optional.of(task));
        Mockito.when(categoryRepository.save(category))
                .thenReturn(expectedCategory);
        Optional<Category> optionalCategory = categoryService.removeTaskFromCategory(category, user, 1L);
        assertTrue(optionalCategory.isPresent());
        assertEquals(expectedCategory, optionalCategory.get());
    }
}