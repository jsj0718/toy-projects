package me.jsj.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class PrintJsonTest {

    @Test
    void kafkaMessageTest() throws JsonProcessingException {
        String s = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int32\",\"optional\":false,\"field\":\"id\"},{\"type\":\"string\",\"optional\":true,\"field\":\"user_id\"},{\"type\":\"string\",\"optional\":true,\"field\":\"pwd\"},{\"type\":\"string\",\"optional\":true,\"field\":\"name\"},{\"type\":\"int32\",\"optional\":true,\"name\":\"org.apache.kafka.connect.data.Date\",\"version\":1,\"field\":\"created_at\"}],\"optional\":false,\"name\":\"users\"},\"payload\":{\"id\":3,\"user_id\":\"user4\",\"pwd\":\"test4444\",\"name\":\"test user 4\",\"created_at\":20074}}";

        ObjectMapper mapper = new ObjectMapper();

        Map map = mapper.readValue(s, Map.class);

        String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

        System.out.println(result);
    }
}
