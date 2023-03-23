package cz.muni.fi.pa165.core.controller;

import cz.muni.fi.pa165.model.dto.UserDto;
import cz.muni.fi.pa165.model.dto.UserRegisterDto;
import cz.muni.fi.pa165.core.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(userRegisterDto));
    }
}
