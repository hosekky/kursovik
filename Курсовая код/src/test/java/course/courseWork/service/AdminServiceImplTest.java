package course.courseWork.service;

import course.courseWork.model.UserAuthority;
import course.courseWork.model.UserRoles;
import course.courseWork.model.Users;
import course.courseWork.repository.UserRepository;
import course.courseWork.repository.UserRolesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class AdminServiceImplTest {

    private final UserRolesRepository userRolesRepository = Mockito.mock(UserRolesRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final AdminServiceImpl adminService = new AdminServiceImpl(userRepository);

//    @DisplayName("delete user by username")
//    @Test
//    void deleteUserByUsername() {
//        Users admin = new Users();
//        admin.setUsername("admin");
//        userRepository.save(admin);
//        userRolesRepository.save(new UserRoles(null, UserAuthority.ROLE_ADMIN, admin));
//        Users user = new Users();
//        user.setUsername("user");
//        userRepository.save(user);
//        userRolesRepository.save(new UserRoles(null, UserAuthority.ROLE_USER, user));
//        when(userRepository.findByUsername("user"))
//                .thenReturn(Optional.of(user));
//        doNothing().when(userRepository).delete(user);
//        assertDoesNotThrow(() -> adminService.deleteUserByUsername("user", admin));
//    }

    @Test
    void getAllUsers() {
    }
}