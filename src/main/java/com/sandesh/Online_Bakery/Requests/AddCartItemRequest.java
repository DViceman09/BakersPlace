package com.sandesh.Online_Bakery.Requests;

//import com.sandesh.Online_Bakery.Model.IngredientsItem;
import lombok.Data;

@Data
public class AddCartItemRequest {
    private Long foodId;
    private int quantity;
}
