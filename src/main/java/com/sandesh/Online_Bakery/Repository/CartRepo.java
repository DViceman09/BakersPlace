package com.sandesh.Online_Bakery.Repository;

import com.sandesh.Online_Bakery.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Long>
{

}
