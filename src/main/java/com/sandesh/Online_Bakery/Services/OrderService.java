package com.sandesh.Online_Bakery.Services;

import com.sandesh.Online_Bakery.Model.Order;
import com.sandesh.Online_Bakery.Model.UserEntity;
import com.sandesh.Online_Bakery.Requests.OrderRequest;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest order, UserEntity user) throws Exception;

    public Order updateOrder(Long orderId, String status) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getUserOrders(Long userId) throws Exception;

    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception;

    public Order findOrderByOrderId(Long id) throws Exception;
}
