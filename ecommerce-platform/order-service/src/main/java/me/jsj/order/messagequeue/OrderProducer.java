package me.jsj.order.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jsj.order.dto.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderDto send(String topic, OrderDto orderDto) {
        KafkaOrderDto kafkaOrderDto = KafkaOrderDto.builder()
                .schema(getSchema())
                .payload(getPayload(orderDto))
                .build();

        kafkaTemplate.send(topic, getJsonMessage(kafkaOrderDto));
        log.info("Order Producer sent data from the Order microservice: {}", kafkaOrderDto);

        return orderDto;
    }

    private static Schema getSchema() {
        return Schema.builder()
                .type("struct")
                .fields(getFields())
                .optional(false)
                .name("orders")
                .build();
    }

    private static List<Field> getFields() {
        return List.of(
                new Field("string", true, "order_id"),
                new Field("string", true, "user_id"),
                new Field("string", true, "product_id"),
                new Field("int32", true, "qty"),
                new Field("int32", true, "unit_price"),
                new Field("int32", true, "total_price")
        );
    }

    private Payload getPayload(OrderDto orderDto) {
        return Payload.builder()
                .order_id(orderDto.getOrderId())
                .user_id(orderDto.getUserId())
                .product_id(orderDto.getProductId())
                .qty(orderDto.getQty())
                .unit_price(orderDto.getUnitPrice())
                .total_price(orderDto.getTotalPrice())
                .build();
    }

    private String getJsonMessage(KafkaOrderDto kafkaOrderDto) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(kafkaOrderDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
