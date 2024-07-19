package com.sandesh.Online_Bakery.Requests;

import com.sandesh.Online_Bakery.Model.CartItem;
import lombok.Data;

@Data
public class UpdateCartItemReq {
    private Long cartItemId;
    private int quantity;
}
