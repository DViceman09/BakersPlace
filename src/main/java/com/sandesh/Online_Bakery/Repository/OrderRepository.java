package com.sandesh.Online_Bakery.Repository;

import com.sandesh.Online_Bakery.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findOrderByCustomer_Id(Long userId);

    public List<Order> findOrderByRestaurantRestaurantId(Long restaurantId);
}
