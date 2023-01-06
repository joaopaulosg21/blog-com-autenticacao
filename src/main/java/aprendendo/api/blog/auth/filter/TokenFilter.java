package aprendendo.api.blog.auth.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import aprendendo.api.blog.auth.service.TokenService;
import aprendendo.api.blog.entities.User;
import aprendendo.api.blog.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TokenFilter extends OncePerRequestFilter{
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;
    
    protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain) 
    throws ServletException,IOException{
        String token = getToken(req);
        boolean isValid = tokenService.isValid(token);
        if(isValid) {
            authenticate(token);
        }
        doFilter(req,res,chain);
    }

    private String getToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.split(" ")[1];
    }

    private void authenticate(String token) {
        Long id = tokenService.getId(token);
        Optional<User> verify = userRepository.findById(id);
        if(verify.isPresent()) {
            User user = verify.get();
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(user.getEmail(),null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePassword);
        }
    }
}
