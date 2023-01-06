package aprendendo.api.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import aprendendo.api.blog.auth.service.TokenService;
import aprendendo.api.blog.entities.User;
import aprendendo.api.blog.entities.DTO.LoginDTO;
import aprendendo.api.blog.entities.DTO.PostDTO;
import aprendendo.api.blog.entities.DTO.TokenDTO;
import aprendendo.api.blog.entities.DTO.UserDTO;
import aprendendo.api.blog.exceptions.user.EmailUsedException;
import aprendendo.api.blog.repository.PostRepository;
import aprendendo.api.blog.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired
    PostRepository postRepository;

    public UserDTO addUser(User user) {
        Optional<User> verify = userRepository.findByEmail(user.getEmail());
        if(verify.isEmpty()) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            return userRepository.save(user).toDTO();
        }

        throw new EmailUsedException();
    }

    public TokenDTO login(LoginDTO login){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generate(authentication);
        return TokenDTO.builder().token(token).build();
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(x -> x.toDTO()).toList();
    }

    public List<PostDTO> findMyPosts(String token) {
        Long id = tokenService.getId(token);
        return postRepository.findAllByUser(id).stream().map(x -> x.toDTO()).toList();
    }
}
