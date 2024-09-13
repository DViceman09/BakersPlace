package com.sandesh.Online_Bakery.Repository;

import com.sandesh.Online_Bakery.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.customer.id = :userId")
    public List<Order> findOrderByCustomer_Id(Long userId);

    @Query("SELECT o FROM Order o WHERE o.restaurant.restaurantId =:restaurantId")
    public List<Order> findOrderByRestaurantRestaurantId(Long restaurantId);
}
