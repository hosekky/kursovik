package course.courseWork.controller;

import course.courseWork.exceptions.CategoryNotFoundException;
import course.courseWork.exceptions.TaskNotFoundException;
import course.courseWork.model.Category;
import course.courseWork.model.Users;
import course.courseWork.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Object> addCategoryByUser(@RequestBody Category category, @AuthenticationPrincipal Users users) {
        Object result = categoryService.addCategoryByUser(category, users);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategoriesByUser(@AuthenticationPrincipal Users users) {
        List<Category> categories = categoryService.getAllCategoriesByUser(users);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryByIdAndUser(@PathVariable Long categoryId, @AuthenticationPrincipal Users users) throws CategoryNotFoundException {
        Optional<Category> category = categoryService.getCategoryByIdAndUser(categoryId, users);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategoryByCategoryIdAndUsers(@PathVariable Long categoryId, @AuthenticationPrincipal Users users) throws CategoryNotFoundException {

        Optional<Category> category = categoryService.getCategoryByIdAndUser(categoryId, users);
        if (category.isPresent()) {
            categoryService.deleteCategoryByCategoryIdAndUsers(category.orElse(null), users);
            return ResponseEntity.ok("Category deleted successfully!");
        } else {
            throw new CategoryNotFoundException();
        }
    }

    @PutMapping
    public ResponseEntity<Category> updateCategoryByCategoryIdAndUsers(@RequestParam Long categoryId, @RequestBody Category updateCategory, @AuthenticationPrincipal Users users) throws CategoryNotFoundException {
        categoryService.updateCategoryByCategoryIdAndUsers(categoryId, updateCategory, users);
        return ResponseEntity.ok(updateCategory);
    }

    @PostMapping("/{categoryId}/tasks")
    public ResponseEntity<Category> addTaskToCategory(@PathVariable Long categoryId, @RequestParam Long taskId, @AuthenticationPrincipal Users users) {
        try {
            Optional<Category> category = categoryService.getCategoryByIdAndUser(categoryId, users);
            Optional<Category> updatedCategory = categoryService.addTaskToCategory(category.orElse(null), users, taskId);
            if (updatedCategory.isPresent()) {
                return ResponseEntity.ok(updatedCategory.get());
            } else {
                throw new CategoryNotFoundException();
            }
        } catch (CategoryNotFoundException | TaskNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{categoryId}/tasks")
    public ResponseEntity<Category> removeTaskFromCategory(@PathVariable Long categoryId, @RequestParam Long taskId, @AuthenticationPrincipal Users users) {
        try {
            Optional<Category> category = categoryService.getCategoryByIdAndUser(categoryId, users);
            Optional<Category> updatedCategory = categoryService.removeTaskFromCategory(category.orElse(null), users, taskId);
            if (updatedCategory.isPresent()) {
                return ResponseEntity.ok(updatedCategory.get());
            } else {
                throw new CategoryNotFoundException();
            }
        } catch (CategoryNotFoundException | TaskNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


