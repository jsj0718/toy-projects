package me.jsj.order.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jsj.order.dto.OrderDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderDto send(String topic, OrderDto orderDto) {
        kafkaTemplate.send(topic, getOrderDtoJson(orderDto));
        log.info("Kafka Producer sent data from the Order microservice: {}", orderDto);

        return orderDto;
    }

    private String getOrderDtoJson(OrderDto orderDto) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(orderDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
