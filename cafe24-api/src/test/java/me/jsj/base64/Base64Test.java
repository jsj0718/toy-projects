package me.jsj.base64;

import org.apache.commons.codec.binary.Base64;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class Base64Test {
    @Test
    void encodeTest() {
        String s = "test";
        byte[] encodeBytes = Base64.encodeBase64(s.getBytes());

        System.out.println(new String(encodeBytes));

        byte[] decodeBytes = Base64.decodeBase64(encodeBytes);
        Assertions.assertThat(new String(decodeBytes)).isEqualTo(s);
    }
}
