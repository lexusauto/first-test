package booking.config;

import org.junit.jupiter.api.BeforeAll;

import static booking.util.RestAssuredSpec.setupRestAssured;

public class BaseApiTest {
    @BeforeAll
    static void setUp() {
        setupRestAssured();
    }
}
