package course.courseWork.service;

import course.courseWork.exceptions.UsernameAlreadyExistsException;
import course.courseWork.model.UserAuthority;
import course.courseWork.model.UserRoles;
import course.courseWork.model.Users;
import course.courseWork.repository.UserRepository;
import course.courseWork.repository.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final UserRolesRepository usersRolesRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void registration(String username, String password) {
        if (userRepository.findByUsername(username).isEmpty()) {
            Users users = userRepository.save(
                    new Users()
                            .setUsername(username)
                            .setPassword(passwordEncoder.encode(password))
                            .setLocked(false)
                            .setExpired(false)
                            .setEnabled(true)
            );
            usersRolesRepository.save(new UserRoles(null, UserAuthority.ROLE_USER, users));
        } else {
            throw new UsernameAlreadyExistsException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}