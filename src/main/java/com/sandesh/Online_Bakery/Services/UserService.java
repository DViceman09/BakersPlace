package com.sandesh.Online_Bakery.Services;

import com.sandesh.Online_Bakery.Model.UserEntity;


public interface UserService {
    public UserEntity findUserByJwtToken(String jwt) throws Exception;

    public UserEntity findUserByEmail(String email) throws Exception;
}
