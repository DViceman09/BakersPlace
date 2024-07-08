package com.sandesh.Online_Bakery.Repository;

import com.sandesh.Online_Bakery.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    public UserEntity findByEmail(String username);
}
