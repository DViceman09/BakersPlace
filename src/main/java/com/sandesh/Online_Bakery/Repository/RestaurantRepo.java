package com.sandesh.Online_Bakery.Repository;

import com.sandesh.Online_Bakery.Model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RestaurantRepo extends JpaRepository<Restaurant, Long>
{
        @Query("SELECT r FROM Restaurant r WHERE lower(r.restaurant_name) LIKE lower(concat('%', :query, '%'))" +
                "OR lower(r.cuisineType) LIKE lower(concat('%',:query,'%'))")
        List<Restaurant> findBySearchQuery(String query);

        Restaurant findByOwnerId(Long userId);

}
