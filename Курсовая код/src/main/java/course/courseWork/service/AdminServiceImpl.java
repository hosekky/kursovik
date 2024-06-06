package course.courseWork.service;

import course.courseWork.model.UserAuthority;
import course.courseWork.model.Users;
import course.courseWork.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    public void deleteUserByUsername(String username, Users currentUsers) {
        Users usersToDelete = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (currentUsers.getAuthorities().contains(UserAuthority.ROLE_ADMIN) || !usersToDelete.getAuthorities().contains(UserAuthority.ROLE_ADMIN)) {
            userRepository.delete(usersToDelete);
        } else {
            throw new AccessDeniedException("You do not have permission to delete this user");
        }
    }

    public List<Users> getAllUsers(Users currentUsers) {
        List<Users> allUsers = userRepository.findAll();
        if (currentUsers.getAuthorities().contains(UserAuthority.ROLE_USER)) {
            return allUsers;
        } else {
            throw new AccessDeniedException("You do not have permission to get all users");
        }
    }
}
