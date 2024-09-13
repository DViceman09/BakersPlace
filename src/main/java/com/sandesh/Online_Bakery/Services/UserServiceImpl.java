package com.sandesh.Online_Bakery.Services;

import com.sandesh.Online_Bakery.Model.UserEntity;
import com.sandesh.Online_Bakery.Repository.UserRepo;
import com.sandesh.Online_Bakery.configurations.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtProvider jwtProvider;


    @Override
    public UserEntity findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        UserEntity user = userRepo.findByEmail(email);
        return user;
    }

    @Override
    public UserEntity findUserByEmail(String email) throws Exception {
        UserEntity user = userRepo.findByEmail(email);
        if(user == null) {
            throw new Exception("User not found");
        }

        return user;
    }
}
