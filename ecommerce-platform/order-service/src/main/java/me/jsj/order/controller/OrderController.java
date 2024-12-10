package me.jsj.order.controller;

import lombok.RequiredArgsConstructor;
import me.jsj.order.domain.OrderEntity;
import me.jsj.order.dto.OrderDto;
import me.jsj.order.service.OrderService;
import me.jsj.order.vo.RequestOrder;
import me.jsj.order.vo.ResponseOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/order-service")
@RestController
public class OrderController {
    private final Environment env;
    private final OrderService orderService;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in Order Service on Port: %s",
                env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
                                                     @RequestBody RequestOrder orderDetails) {
        ModelMapper mapper = getMapper();

        OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);

        OrderDto savedOrderdto = orderService.createOrder(orderDto);

        ResponseOrder response = mapper.map(savedOrderdto, ResponseOrder.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable("userId") String userId) {
        ModelMapper mapper = getMapper();

        List<OrderEntity> orders = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> response = orders
                .stream()
                .map(orderEntity -> mapper.map(orderEntity, ResponseOrder.class))
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ResponseOrder> getOrder(@PathVariable("orderId") String orderId) {
        ModelMapper mapper = getMapper();

        OrderDto orderDto = orderService.getOrderByOrderId(orderId);
        ResponseOrder response = mapper.map(orderDto, ResponseOrder.class);

        return ResponseEntity.ok(response);
    }

    private static ModelMapper getMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }
}
