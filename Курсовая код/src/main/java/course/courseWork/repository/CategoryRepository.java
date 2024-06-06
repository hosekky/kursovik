package course.courseWork.repository;

import course.courseWork.model.Category;
import course.courseWork.model.Task;
import course.courseWork.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> getAllCategoriesByUsers(Users users);

    Optional<Category> findCategoryByCategoryIdAndUsers(Long categoryId, Users users);

    Optional<Category> getCategoryByCategoryIdAndUsers(Long categoryId, Users users);

    List<Category> getAllByUsersIsNull();
}
