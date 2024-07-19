package com.sandesh.Online_Bakery.Services;

import com.sandesh.Online_Bakery.Model.Category;
import com.sandesh.Online_Bakery.Model.Food;
import com.sandesh.Online_Bakery.Model.Restaurant;
import com.sandesh.Online_Bakery.Repository.FoodRepository;
import com.sandesh.Online_Bakery.Requests.CreateFoodRequest;
import com.sandesh.Online_Bakery.Requests.UpdateFoodReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;


    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setFood_name(req.getName());
        food.setImages(req.getImages());
        food.setPrice(req.getPrice());
//        food.setIngredients(req.getItems());
        food.setSeasonal(req.isSeasonal());
        food.setVeg(req.isVeg());

        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    @Override
    public void deleteFood(Long FoodId) throws Exception {
        Food food = findFoodById(FoodId);
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public List<Food> getFoodByRestaurantId(Long restaurantId, boolean isVeg, boolean isNonVeg, boolean isSeasonal, String category) {
        List<Food> foods = foodRepository.findByRestaurant_RestaurantId(restaurantId);

        if(isVeg)
        {
            foods = filterByVeg(foods,isVeg);
        }
        if(isNonVeg)
        {
            foods = filterByisNonVeg(foods, isNonVeg);
        }
        if(isSeasonal)
        {
            foods = filterByisSeasonal(foods, isSeasonal);
        }
        if(category!=null && !category.equals(""))
        {
            foods = filterByCategory(foods, category);
        }
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String category) {
        return foods.stream().filter(food -> {
            if(food.getFoodCategory()!= null)
            {
                return food.getFoodCategory().getName().equals(category);
            }
            else
            {
                return false;
            }
        }).collect(Collectors.toList());
    }

    private List<Food> filterByisSeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByisNonVeg(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(food -> !food.isVeg()).collect(Collectors.toList());
    }

    private List<Food> filterByVeg(List<Food> foods, boolean isVeg) {
        return foods.stream().filter(food -> food.isVeg()==isVeg).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long FoodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(FoodId);
        if(optionalFood.isEmpty())
        {
            throw new Exception("Food does not exist");
        }
        return optionalFood.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long FoodId) throws Exception {
        Food food = findFoodById(FoodId);
        food.setAvailable(food.isAvailable());
        return foodRepository.save(food);
    }

    @Override
    public Food updateFood(Long FoodId, UpdateFoodReq foodReq) throws Exception {
        Food food = findFoodById(FoodId);
        food.setDescription(foodReq.getDescription());
        food.setFood_name(food.getFood_name());
        food.setPrice(foodReq.getPrice());
        food.setImages(foodReq.getImages());
        food.setVeg(foodReq.isVeg());
        return food;
    }
}
