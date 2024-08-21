package com.sandesh.Online_Bakery.Services;

import com.sandesh.Online_Bakery.Model.Cart;
import com.sandesh.Online_Bakery.Model.CartItem;
import com.sandesh.Online_Bakery.Model.Food;
import com.sandesh.Online_Bakery.Model.UserEntity;
import com.sandesh.Online_Bakery.Repository.CartItemRepository;
import com.sandesh.Online_Bakery.Repository.CartRepository;
import com.sandesh.Online_Bakery.Repository.FoodRepository;
import com.sandesh.Online_Bakery.Requests.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;


    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(req.getFoodId());
        Cart cart = cartRepository.findByCustomer_Id(user.getId());

        for(CartItem cartItem : cart.getItem())
        {
            if(cartItem.getFood().equals(food))
            {
                int newQuan = cartItem.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuan);
            }
        }
        CartItem cartItem = new CartItem();
        cartItem.setFood(food);
        cartItem.setCart(cart);
        cartItem.setQuantity(req.getQuantity());
        cartItem.setTotalPrice(req.getQuantity() * food.getPrice());

        CartItem savedCart = cartItemRepository.save(cartItem);

        cart.getItem().add(savedCart);
        cartRepository.save(cart);
        return savedCart;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItem, int quantity) throws Exception {
        Optional<CartItem> cartItem1 = cartItemRepository.findById(cartItem);
        if(cartItem1.isEmpty())
        {
            throw new Exception("Cart Item not found");
        }
        CartItem item = cartItem1.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);
        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long CartItemId, String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomer_Id(user.getId());

        Optional<CartItem> cartItem = cartItemRepository.findById(CartItemId);
        if(cartItem.isEmpty())
        {
            throw new Exception("Cart Item not found");
        }
        CartItem item = cartItem.get();
        cart.getItem().remove(item);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {
        long total = 0L;
        for(CartItem cartItem: cart.getItem())
        {
            total += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optional = cartRepository.findById(id);
        if(optional.isEmpty())
        {
            throw new Exception("Cart not found");
        }
        return optional.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
//        UserEntity User = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomer_Id(userId);
        cart.setTotal(calculateCartTotal(cart));
        return cartRepository.findByCustomer_Id(userId);
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
//        UserEntity user = userService.findUserByJwtToken(jwt);
        Cart cart = findCartByUserId(userId);
        cart.getItem().clear();
        return cartRepository.save(cart);
    }
}
