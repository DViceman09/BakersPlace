package com.sandesh.Online_Bakery.Controller;

import com.sandesh.Online_Bakery.Model.Order;
import com.sandesh.Online_Bakery.Model.UserEntity;
import com.sandesh.Online_Bakery.Requests.OrderRequest;
import com.sandesh.Online_Bakery.Services.OrderService;
import com.sandesh.Online_Bakery.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestBody OrderRequest req,
                                                       @RequestHeader("Authorization") String jwt,
                                                       @RequestParam(required = false) String orderStatus,
                                                       @PathVariable Long id) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        List<Order> order = orderService.getRestaurantOrders(id, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@RequestBody OrderRequest req,
                                                       @RequestHeader("Authorization") String jwt,
                                                       @PathVariable String orderStatus,
                                                       @PathVariable Long id) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrder(id, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
