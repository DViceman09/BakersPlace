package com.sandesh.Online_Bakery.Services;

import com.sandesh.Online_Bakery.Model.Cart;
import com.sandesh.Online_Bakery.Model.CartItem;
import com.sandesh.Online_Bakery.Requests.AddCartItemRequest;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception;

    public CartItem updateCartItemQuantity(Long cartItem, int quantity) throws Exception;

    public Cart removeItemFromCart(Long CartItemId, String jwt) throws Exception;

    public Long calculateCartTotal(Cart cart) throws Exception;

    public Cart findCartById(Long id) throws Exception;

    public Cart findCartByUserId(Long userId) throws Exception;

    public Cart clearCart(Long userId) throws Exception;

}
