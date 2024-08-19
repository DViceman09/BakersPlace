package com.sandesh.Online_Bakery.Controller;


import com.sandesh.Online_Bakery.Model.Cart;
import com.sandesh.Online_Bakery.Model.USER_ROLE;
import com.sandesh.Online_Bakery.Model.UserEntity;
import com.sandesh.Online_Bakery.Repository.CartRepository;
import com.sandesh.Online_Bakery.Repository.UserRepo;
import com.sandesh.Online_Bakery.Requests.LoginRequest;
import com.sandesh.Online_Bakery.Response.AuthResponse;
import com.sandesh.Online_Bakery.Services.CustomerUserDetailsService;
import com.sandesh.Online_Bakery.configurations.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserEntity user) throws Exception {
        UserEntity isEmailExist =  userRepo.findByEmail(user.getEmail());
        if(isEmailExist != null)
        {
            throw new Exception("Email already in use. Please use a new email id");
        }

        UserEntity User = new UserEntity();
        User.setEmail(user.getEmail());
        User.setUsername(user.getUsername());
        User.setRole(user.getRole());
        User.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity savedUser = userRepo.save(User);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepo.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setJwt(jwt);
        authResponse.setMessage("Registration is successful");
        authResponse.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> Signin(@RequestBody LoginRequest loginRequest)
    {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(email, password);

        Collection<?extends GrantedAuthority> authority = authentication.getAuthorities();

        String role = authority.isEmpty()?null:authority.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successful");
        authResponse.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password)
    {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

        if(userDetails == null)
        {
            throw new BadCredentialsException("Invalid username");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword()))
        {
            throw new BadCredentialsException("Invalid password!!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
