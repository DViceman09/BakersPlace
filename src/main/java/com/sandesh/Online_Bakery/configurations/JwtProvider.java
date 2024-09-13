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

    private SecretKey key = Keys.hmacShaKeyFor("fiugndofnkgsdofjbgojaddisajbfojsgpksdnagpkabfogjisfbgsjifogboafuibgoaifn".getBytes());

    public String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = this.populateAuthorities(authorities);
        String jwt = Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + 86400000000L)).claim("email", auth.getName()).claim("authorities", roles).signWith(this.key).compact();
        return jwt;
    }

    public String getEmailFromJwtToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = (Claims)Jwts.parser().setSigningKey(this.key).build().parseClaimsJws(jwt).getBody();
        String email = String.valueOf(claims.get("email"));
        return email;
    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> auths = new HashSet();
        Iterator var3 = collection.iterator();

        while(var3.hasNext()) {
            GrantedAuthority authority = (GrantedAuthority)var3.next();
            auths.add(authority.getAuthority());
        }

        return String.join(",", auths);
    }
}
