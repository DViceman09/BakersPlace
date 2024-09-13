package com.sandesh.Online_Bakery.Controller;

import com.sandesh.Online_Bakery.Model.Food;
import com.sandesh.Online_Bakery.Model.Restaurant;
import com.sandesh.Online_Bakery.Model.UserEntity;
import com.sandesh.Online_Bakery.Requests.CreateFoodRequest;
import com.sandesh.Online_Bakery.Requests.UpdateFoodReq;
import com.sandesh.Online_Bakery.Response.MessageResponse;
import com.sandesh.Online_Bakery.Services.FoodService;
import com.sandesh.Online_Bakery.Services.RestaurantService;
import com.sandesh.Online_Bakery.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req, req.getCategory(), restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception {

        UserEntity user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);

        MessageResponse res = new MessageResponse();
        res.setMessage("Food deleted successfully");

        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateAvailabilityStatus(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception {

        UserEntity user = userService.findUserByJwtToken(jwt);
        Food food =  foodService.updateAvailabilityStatus(id);

        return new ResponseEntity<>(food , HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Food> updateFood(@RequestHeader("Authorization") String jwt, @PathVariable Long id,
                                           @RequestBody UpdateFoodReq foodReq) throws Exception {

        UserEntity user = userService.findUserByJwtToken(jwt);
        Food food =  foodService.updateFood(id, foodReq);

        return new ResponseEntity<>(food , HttpStatus.OK);
    }
}
