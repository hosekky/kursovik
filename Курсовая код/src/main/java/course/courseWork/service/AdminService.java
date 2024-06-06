package course.courseWork.service;

import course.courseWork.model.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    List<Users> getAllUsers(Users currentUsers);

    void deleteUserByUsername(String username, Users currentUsers);

}
