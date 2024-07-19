package com.sandesh.Online_Bakery.Repository;

import com.sandesh.Online_Bakery.Model.Cart;
import com.sandesh.Online_Bakery.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
