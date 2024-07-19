//package com.sandesh.Online_Bakery.Services;
//
//import com.sandesh.Online_Bakery.Model.IngredientCategory;
//import com.sandesh.Online_Bakery.Model.IngredientsItem;
//import com.sandesh.Online_Bakery.Model.Restaurant;
//import com.sandesh.Online_Bakery.Repository.IngredientCategoryRepository;
//import com.sandesh.Online_Bakery.Repository.IngredientsItemRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class IngredientServiceImp implements IngredientsService{
//
//    @Autowired
//    private IngredientsItemRepository ingredientsItemRepository;
//
//    @Autowired
//    private IngredientCategoryRepository ingredientCategoryRepository;
//
//    @Autowired
//    private RestaurantService restaurantService;
//
//
//    @Override
//    public IngredientCategory createIngredientCategory(String name, Long id) throws Exception {
//        Restaurant restaurant = restaurantService.findRestaurantById(id);
//
//        IngredientCategory category = new IngredientCategory();
//        category.setRestaurant(restaurant);
//        category.setName(name);
//        return ingredientCategoryRepository.save(category);
//    }
//
//    @Override
//    public IngredientCategory findIngredientById(Long id) throws Exception {
//        Optional<IngredientCategory> optional = ingredientCategoryRepository.findById(id);
//
//        if(optional.isEmpty())
//        {
//            throw new Exception("Ingredient category not found");
//        }
//        return optional.get();
//    }
//
//    @Override
//    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
//        restaurantService.findRestaurantById(id);
//
//        return ingredientCategoryRepository.findByRestaurant_RestaurantId(id);
//    }
//
//    @Override
//    public IngredientsItem createIngredientItems(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
//        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
//        IngredientCategory category = findIngredientById(categoryId);
//
//        IngredientsItem item =  new IngredientsItem();
//        item.setIngredient_name(ingredientName);
//        item.setRestaurant(restaurant);
//        item.setCategory(category);
//
//        IngredientsItem ingredient =  ingredientsItemRepository.save(item);
//        category.getIngredients().add(ingredient);
//
//        return ingredient;
//    }
//
//    @Override
//    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {
//        return ingredientsItemRepository.findByRestaurant_RestaurantId(restaurantId);
//    }
//
//    @Override
//    public IngredientsItem updateStock(Long id) throws Exception {
//        Optional<IngredientsItem> optional = ingredientsItemRepository.findById(id);
//        if(optional.isEmpty())
//        {
//            throw new Exception("Ingredient not found");
//        }
//        IngredientsItem ingredientsItem = optional.get();
//        ingredientsItem.setStock(!ingredientsItem.isStock());
//        return ingredientsItemRepository.save(ingredientsItem);
//    }
//}
