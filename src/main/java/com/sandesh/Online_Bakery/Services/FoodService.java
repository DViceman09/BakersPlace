package com.sandesh.Online_Bakery.Services;

import com.sandesh.Online_Bakery.Model.Category;
import com.sandesh.Online_Bakery.Model.Food;
import com.sandesh.Online_Bakery.Model.Restaurant;
import com.sandesh.Online_Bakery.Requests.CreateFoodRequest;
import com.sandesh.Online_Bakery.Requests.UpdateFoodReq;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    public void deleteFood(Long FoodId) throws Exception;

    public List<Food> getFoodByRestaurantId(Long restaurantId, boolean isVeg, boolean isNonVeg, boolean isSeasonal, String category);

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long FoodId) throws Exception;

    public Food updateAvailabilityStatus(Long FoodId) throws Exception;

    public Food updateFood(Long FoodId, UpdateFoodReq req) throws Exception;
}
