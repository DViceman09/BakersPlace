package com.sandesh.Online_Bakery.Services;

import com.sandesh.Online_Bakery.DTO.RestaurantDTO;
import com.sandesh.Online_Bakery.Model.Address;
import com.sandesh.Online_Bakery.Model.ContactInformation;
import com.sandesh.Online_Bakery.Model.Restaurant;
import com.sandesh.Online_Bakery.Model.UserEntity;
import com.sandesh.Online_Bakery.Repository.AddressRepo;
import com.sandesh.Online_Bakery.Repository.RestaurantRepo;
import com.sandesh.Online_Bakery.Repository.UserRepo;
import com.sandesh.Online_Bakery.Requests.CreateRestaurantReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Restaurant createRestaurant(CreateRestaurantReq req, UserEntity user) {
        Address address = addressRepo.save(req.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurant_address(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImagesList());
        restaurant.setRestaurant_name(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepo.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurant_id, CreateRestaurantReq updatedRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurant_id);

        if(restaurant.getCuisineType()!=null)
        {
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }

        if(restaurant.getDescription()!=null)
        {
            restaurant.setDescription(updatedRestaurant.getDescription());
        }

        if(restaurant.getRestaurant_name()!=null)
        {
            restaurant.setRestaurant_name(updatedRestaurant.getName());
        }

        if(restaurant.getContactInformation()!=null)
        {
            restaurant.setContactInformation(updatedRestaurant.getContactInformation());
        }
        return restaurantRepo.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurant_id) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurant_id);
        restaurantRepo.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepo.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurant_id) throws Exception {
        Optional<Restaurant> opt = restaurantRepo.findById(restaurant_id);
        if(opt.isEmpty())
        {
            throw new Exception("Restaurant not found with id " + restaurant_id);
        }
            return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long user_id) throws Exception {
        Restaurant restaurant = restaurantRepo.findByOwnerId(user_id);
        if(restaurant == null)
        {
            throw new Exception("Restaurant not found with owner_id " + user_id);
        }
        return restaurant;
    }

    @Override
    public RestaurantDTO AddToFavorites(Long restaurant_id, UserEntity user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurant_id);
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setDescription(restaurant.getDescription());
        restaurantDTO.setTitle(restaurant.getRestaurant_name());
        restaurantDTO.setImages(restaurant.getImages());
        restaurantDTO.setId(restaurant.getRestaurant_id());

        boolean isFavorite = false;
        List<RestaurantDTO> favorites = user.getFavorites();
        for(RestaurantDTO favorite : favorites)
        {
            if(favorite.getId().equals(restaurant_id)) {
                isFavorite = true;
                break;
            }
        }

        if(isFavorite)
        {
            favorites.removeIf(favorite -> favorite.getId().equals(restaurant_id));
        }
        else
        {
            favorites.add(restaurantDTO);
        }

        userRepo.save(user);
        return restaurantDTO;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        if(restaurant.isOpen())
        {
            restaurant.setOpen(false);
        }
        else if(!restaurant.isOpen()) {
            restaurant.setOpen(true);
        }
        return restaurantRepo.save(restaurant);
    }
}
