package aprendendo.api.blog.auth.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import aprendendo.api.blog.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    @Value("${jwt.expiration}")
    private String exp;

    @Value("${jwt.secret}")
    private String secret;

    public String generate(Authentication authentication){
        Long id = ((User)authentication.getPrincipal()).getId();
        Date now = new Date();
        return Jwts.builder()
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + Long.parseLong(exp)))
        .setSubject(id.toString())
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch(Exception exc) {
            return false;
        }
    }
    
    public Long getId(String token) {
        Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Long.valueOf(body.getSubject());
    }
}
