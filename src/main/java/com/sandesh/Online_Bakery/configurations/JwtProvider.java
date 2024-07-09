package com.sandesh.Online_Bakery.configurations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class JwtProvider {

    private SecretKey key = Keys.hmacShaKeyFor(JWTConstants.SECRET_KEY.getBytes());
    public String generateToken(Authentication auth) {
        Collection<?extends GrantedAuthority> authority = auth.getAuthorities();
        String roles = populateAuthorities(authority);

        String jwt = Jwts.builder().issuedAt(new Date())
                .setExpiration((new Date(new Date().getTime()+ 86400000)))
                .claim("email", auth.getName())
                .claim("authority", roles)
                .signWith(key).compact();
        return jwt;
    }

    public String getEmailFromJWTToken(String jwt)
    {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        String email = String.valueOf(claims.get("email"));
        return email;
    }

    private String populateAuthorities(Collection<?extends GrantedAuthority> authority) {
        Set<String> authorities = new HashSet<>();
        for(GrantedAuthority grantedAuthority : authority) {
            authorities.add(grantedAuthority.getAuthority());
        }
        return String.join(",", authorities);
    }
}
