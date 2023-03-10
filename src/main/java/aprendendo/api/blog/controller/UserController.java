package aprendendo.api.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aprendendo.api.blog.entities.User;
import aprendendo.api.blog.entities.DTO.LoginDTO;
import aprendendo.api.blog.entities.DTO.PostDTO;
import aprendendo.api.blog.entities.DTO.TokenDTO;
import aprendendo.api.blog.entities.DTO.UserDTO;
import aprendendo.api.blog.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(user));
    } 

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> loginUser(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.login(loginDTO));
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> findMyPosts(@RequestHeader HttpHeaders headers) {
        String token = headers.getFirst("Authorization").split(" ")[1];
        return ResponseEntity.ok(userService.findMyPosts(token));
    }
}
