package com.sandesh.Online_Bakery.configurations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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
                    Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                    String email = String.valueOf(claims.get("email"));
                    String authority = String.valueOf(claims.get("authority"));

                    List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
                }
                catch (Exception e) {
                    throw new BadCredentialsException(("invalid token"));
                }
            }
            filterChain.doFilter(request,response);
    }
}
