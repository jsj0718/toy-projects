package me.jsj.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jsj.order.domain.OrderEntity;
import me.jsj.order.dto.OrderDto;
import me.jsj.order.messagequeue.KafkaProducer;
import me.jsj.order.messagequeue.OrderProducer;
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

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order-service")
@RestController
public class OrderController {
    private final Environment env;
    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in Order Service on Port: %s",
                env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
                                                     @RequestBody RequestOrder orderDetails) {
        log.info("Before add orders data");
        ModelMapper mapper = getMapper();

        OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);

        /* JPA */
        OrderDto savedOrderdto = orderService.createOrder(orderDto);
        ResponseOrder response = mapper.map(savedOrderdto, ResponseOrder.class);

        /* Kafka */
//        orderDto.setOrderId(UUID.randomUUID().toString());
//        orderDto.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());

        /* Send an order to the kafka */
        kafkaProducer.send("example-catalog-topic", orderDto); //서로 다른 서비스 동기화
//        orderProducer.send("orders", orderDto); //같은 서비스 동기화
//        ResponseOrder response = mapper.map(orderDto, ResponseOrder.class);

        log.info("After add orders data");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable("userId") String userId) {
        log.info("Before retrieve orders data");
        ModelMapper mapper = getMapper();

        List<OrderEntity> orders = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> response = orders
                .stream()
                .map(orderEntity -> mapper.map(orderEntity, ResponseOrder.class))
                .toList();

        log.info("After retrieve orders data");
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
