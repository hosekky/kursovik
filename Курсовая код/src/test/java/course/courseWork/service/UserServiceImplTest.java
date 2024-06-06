package course.courseWork.service;

import course.courseWork.exceptions.UsernameAlreadyExistsException;
import course.courseWork.model.Users;
import course.courseWork.repository.UserRepository;
import course.courseWork.repository.UserRolesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);

    private final UserRolesRepository userRolesRepository = mock(UserRolesRepository.class);

    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    private final UserServiceImpl userService = new UserServiceImpl(userRepository, userRolesRepository, passwordEncoder);

    @DisplayName("register user")
    @Test
    void registration() {
        String username = "testUser";
        String password = "123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        userService.registration(username, password);
    }

    @DisplayName("register user - username already exists")
    @Test
    void registrationUsernameAlreadyExists() {
        String username = "testUser";
        String password = "testPassword";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new Users()));

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.registration(username, password));
    }

    @DisplayName("load user by username")
    @Test
    void loadUserByUsername() {
        String username = "testUser";
        Users user = new Users();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername(username);

        assertEquals(user, result);
    }

    @DisplayName("load user by username - username not found")
    @Test
    void loadUserByUsernameUsernameNotFound() {
        String username = "testUser";

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
    }
}