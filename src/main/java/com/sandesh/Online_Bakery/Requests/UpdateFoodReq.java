package com.sandesh.Online_Bakery.Requests;

import com.sandesh.Online_Bakery.Model.Category;
//import com.sandesh.Online_Bakery.Model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class UpdateFoodReq {
    private String name;
    private String description;
    private Long price;
    private List<String> images;
    private boolean isVeg;
}
