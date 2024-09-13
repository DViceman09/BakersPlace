package com.sandesh.Online_Bakery.Controller;

import com.sandesh.Online_Bakery.Model.Food;
import com.sandesh.Online_Bakery.Model.Restaurant;
import com.sandesh.Online_Bakery.Model.UserEntity;
import com.sandesh.Online_Bakery.Requests.CreateFoodRequest;
import com.sandesh.Online_Bakery.Services.FoodService;
import com.sandesh.Online_Bakery.Services.RestaurantService;
import com.sandesh.Online_Bakery.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;


    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
                                            @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.searchFood(name);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getFoodByRestaurantId(@PathVariable Long restaurantId,
                                                @RequestParam(required = false) boolean Veg,
                                                 @RequestParam(required = false) boolean NonVeg,
                                                 @RequestParam(required = false) boolean Seasonal,
                                                 @RequestParam(required = false) String food_category,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.getFoodByRestaurantId(restaurantId, Veg, NonVeg, Seasonal, food_category);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
