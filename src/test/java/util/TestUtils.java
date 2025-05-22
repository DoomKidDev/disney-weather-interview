package util;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.fail;

public class TestUtils {
    @NotNull
    public static String readJsonData() {
        try {
            return new ClassPathResource("data/forecast-api-response.json").getContentAsString(Charset.defaultCharset());
        } catch (IOException e) {
            fail("Unable to read JSON data");
            return null;
        }
    }
}
