package aprendendo.api.blog.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aprendendo.api.blog.auth.service.TokenService;
import aprendendo.api.blog.entities.Post;
import aprendendo.api.blog.entities.User;
import aprendendo.api.blog.entities.DTO.PostDTO;
import aprendendo.api.blog.repository.PostRepository;
import aprendendo.api.blog.repository.UserRepository;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    public PostDTO addPost(Post post,String token) {
        Long id = tokenService.getId(token);
        Optional<User> verify = userRepository.findById(id);
        post.setUser(verify.get());
        post.setDate(LocalDate.now());
        return postRepository.save(post).toDTO();
    }

    public List<PostDTO> findAll() {
        return postRepository.findAll().stream().map(x -> x.toDTO()).toList();
    }
}
