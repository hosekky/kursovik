package course.courseWork.controller;

import course.courseWork.model.Users;
import course.courseWork.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private AdminService adminService;

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUserByUsername(@RequestParam String username, @AuthenticationPrincipal Users users) {
        adminService.deleteUserByUsername(username, users);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<Users>> getAllUsers(@AuthenticationPrincipal Users users) {
        return ResponseEntity.ok(adminService.getAllUsers(users));
    }

}
