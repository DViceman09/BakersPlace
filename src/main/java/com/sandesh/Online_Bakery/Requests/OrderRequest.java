package com.sandesh.Online_Bakery.Requests;

import com.sandesh.Online_Bakery.Model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
