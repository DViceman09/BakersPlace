package com.sandesh.Online_Bakery.Requests;

import com.sandesh.Online_Bakery.Model.Category;
//import com.sandesh.Online_Bakery.Model.IngredientsItem;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price;
    private Category category;
    private List<String> images;
    private Long restaurantId;
    private boolean isVeg;
    private boolean isSeasonal;
    private Date creationDate = new Date();
//    private List<IngredientsItem> items;
}
