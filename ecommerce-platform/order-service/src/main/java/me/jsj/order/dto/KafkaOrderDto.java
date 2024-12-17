package me.jsj.order.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KafkaOrderDto {
    private Schema schema;
    private Payload payload;
}
