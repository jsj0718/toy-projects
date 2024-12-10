package me.jsj.order.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import me.jsj.order.domain.OrderEntity;
import me.jsj.order.domain.OrderRepository;
import me.jsj.order.dto.OrderDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        ModelMapper mapper = getMapper();

        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        OrderEntity orderEntity = mapper.map(orderDto, OrderEntity.class);
        orderRepository.save(orderEntity);

        return mapper.map(orderEntity, OrderDto.class);
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        ModelMapper mapper = getMapper();

        OrderEntity orderEntity = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found by this id: " + orderId));

        return mapper.map(orderEntity, OrderDto.class);
    }

    @Override
    public List<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    private static ModelMapper getMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }
}
