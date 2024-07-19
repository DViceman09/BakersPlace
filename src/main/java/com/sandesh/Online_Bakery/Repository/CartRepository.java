package com.sandesh.Online_Bakery.Repository;

import com.sandesh.Online_Bakery.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    public Cart findByCustomer_Id(Long userId);

}
