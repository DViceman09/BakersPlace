package com.sandesh.Online_Bakery.Services;

import com.sandesh.Online_Bakery.Model.*;
import com.sandesh.Online_Bakery.Repository.*;
import com.sandesh.Online_Bakery.Requests.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest order, UserEntity user) throws Exception {
        Address shippingAddress = order.getDeliveryAddress();
        Address savedAddress = addressRepo.save(shippingAddress);
        if(!user.getAddress().contains(savedAddress))
        {
            user.getAddress().add(savedAddress);
            userRepo.save(user);
        }
        Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());

        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDelivery_address(savedAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItems> orderItems = new ArrayList<>();

        for(CartItem cartItem: cart.getItem())
        {
            OrderItems orderItem= new OrderItems();
            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
//
            orderItem.setTotal_price(cartItem.getTotalPrice());

            OrderItems savedOrder = orderItemRepository.save(orderItem);
            orderItems.add(savedOrder);
        }

        createdOrder.setItems(orderItems);
        createdOrder.setTotal_price(cart.getTotal());

        Order savedOrder = orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);
        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String Orderstatus) throws Exception {
        Order order = findOrderByOrderId(orderId);
        if(Orderstatus.equals("OUT_FOR_DELIVERY") ||
                Orderstatus.equals("DELIVERED") ||
                Orderstatus.equals("COMPLETED")||
                Orderstatus.equals("PENDING"))
        {
            order.setOrderStatus(Orderstatus);
            return orderRepository.save(order);
        }
        throw new Exception("Please select a valid order");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderByOrderId(orderId);
        orderRepository.deleteById(order.getOrder_id());
    }

    @Override
    public List<Order> getUserOrders(Long userId) throws Exception {

        return orderRepository.findOrderByCustomer_Id(userId);
    }

    @Override
    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepository.findOrderByRestaurantRestaurantId(restaurantId);
        if(orderStatus!= null)
        {
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).toList();
        }
        return orders;
    }

    @Override
    public Order findOrderByOrderId(Long id) throws Exception {
        Optional<Order> optional = orderRepository.findById(id);
        if(optional.isEmpty())
        {
            throw new Exception("Order not found");
        }

        return optional.get();
    }
}
