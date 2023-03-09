package cz.muni.fi.pa165.icehockeymanager.controller;

import cz.muni.fi.pa165.icehockeymanager.dto.UserDto;
import cz.muni.fi.pa165.icehockeymanager.dto.UserRegisterDto;
import cz.muni.fi.pa165.icehockeymanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(userRegisterDto));
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok(userService.login(userDto));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Wrong email or password.", HttpStatus.BAD_REQUEST);
        }
    }
}
