package course.courseWork.repository;

import course.courseWork.model.UserRoles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRolesRepository extends CrudRepository<UserRoles, Long> {
}
