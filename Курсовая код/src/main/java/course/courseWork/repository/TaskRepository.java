package course.courseWork.repository;

import course.courseWork.model.Task;
import course.courseWork.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUsers(Users users);

    Optional<Task> findByTaskIdAndUsers(Long id, Users user);

    List<Task> findAllByDeadlineAndUsers(Date deadline, Users users);

    List<Task> findAllByImportanceAndUsers(int importance, Users users);

    List<Task> findAllByDeadlineAndImportanceAndUsers(Date deadline, int importance, Users users);

    Optional<Task> findByTaskIdAndUsersAndDeadline(Long id, Users users, Date deadline);

    List<Task> getAllByUsersIsNull();
}
