package me.jsj.catalog.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jsj.catalog.domain.CatalogEntity;
import me.jsj.catalog.domain.CatalogRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumer {
    private final CatalogRepository catalogRepository;

    @KafkaListener(topics = "example-catalog-topic")
    public void updateQty(String message) {
        log.info(">>>>> Kafka Message: {}", message);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            map = mapper.readValue(message, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        Optional<CatalogEntity> optionalCatalogEntity = catalogRepository.findByProductId((String) map.get("productId"));

        if (optionalCatalogEntity.isPresent()) {
            CatalogEntity catalogEntity = optionalCatalogEntity.get();
            Integer qty = (int) map.get("qty");
            catalogEntity.setStock(catalogEntity.getStock() - qty);
            catalogRepository.save(catalogEntity);
        }
    }
}
