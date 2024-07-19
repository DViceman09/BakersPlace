package com.sandesh.Online_Bakery.Controller;

import com.sandesh.Online_Bakery.Model.CartItem;
import com.sandesh.Online_Bakery.Model.Order;
import com.sandesh.Online_Bakery.Model.UserEntity;
import com.sandesh.Online_Bakery.Requests.AddCartItemRequest;
import com.sandesh.Online_Bakery.Requests.OrderRequest;
import com.sandesh.Online_Bakery.Services.OrderService;
import com.sandesh.Online_Bakery.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(req, user);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestBody OrderRequest req,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        List<Order> order = orderService.getUserOrders(user.getId());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
