package me.jsj.order.service;

import me.jsj.order.domain.OrderEntity;
import me.jsj.order.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    OrderDto getOrderByOrderId(String orderId);
    List<OrderEntity> getOrdersByUserId(String userId);
}
