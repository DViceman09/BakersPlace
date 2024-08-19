package com.sandesh.Online_Bakery.configurations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter
{

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(JWTConstants.JWT_HEADER);
        if(jwt != null)
        {
            jwt = jwt.substring(7);
            try{

                SecretKey key = Keys.hmacShaKeyFor(JWTConstants.SECRET_KEY.getBytes());
                System.out.println("Step 1:Complete");
                Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
                System.out.println("Step 2:Complete");

                String email = String.valueOf(claims.get("email"));
                System.out.println("Step 3:Complete");
                String authority = String.valueOf(claims.get("authority"));
                System.out.println("Step 4:Complete");

                List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
                System.out.println("Step 5:Complete");
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null ,auth);
                System.out.println("Step 6:Complete");
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Step 7:Complete");
            }
            catch (Exception e) {
                throw new BadCredentialsException(("invalid token"));
            }
        }
        filterChain.doFilter(request,response);
    }
}