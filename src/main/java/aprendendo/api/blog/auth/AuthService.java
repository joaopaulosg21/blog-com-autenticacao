package aprendendo.api.blog.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import aprendendo.api.blog.entities.User;
import aprendendo.api.blog.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService{
    @Autowired
    UserRepository userRepository;
    
    public UserDetails loadUserByUsername(String email) {
        Optional<User> verify = userRepository.findByEmail(email);
        if(verify.isPresent()) {
            User user = verify.get();
            return (UserDetails) user;
        }
        throw new UsernameNotFoundException("Email not found");
    }
}
