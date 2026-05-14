package booking.Tests;

import booking.config.BaseApiTest;
import booking.config.BookingConfig;
import booking.dto.AuthRequest;
import booking.dto.AuthResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static booking.config.BookingApiConfig.getBookingConfig;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingAuthNegativeTest extends BaseApiTest {

    private static final BookingConfig config = getBookingConfig();

    @ParameterizedTest
    @CsvSource({"admin, 123",
    "adminus, password123",
    "admin, ",
    ", password123"})
    void authParamTest(String user, String password) {
        AuthResponse resp = given()
                .contentType(ContentType.JSON)
                .body(new AuthRequest(user, password))
                .post(config.bookingUrl() + "/auth")
                .then()
                .statusCode(200)
                .extract().as(AuthResponse.class);

        assertThat(resp.getToken()).isNull();
    }

    @Test
    @DisplayName("Авторизация. Пустое body")
    void emptyBodyTest() {

        AuthResponse resp = given()
                .contentType(ContentType.JSON)
                .body("{}")

                .post(config.bookingUrl() + "/auth")
                .then()
                .statusCode(200)
                .extract().as(AuthResponse.class);

        System.out.println("Токен: '"+resp.getToken()+"'");
        assertThat(resp.getToken()).isNull();

    }

    @Test
    @DisplayName("Авторизация. Без body вообще")
    void withoutBodyTest() {

        AuthResponse resp = given()
                .contentType(ContentType.JSON)
                .post(config.bookingUrl()+ "/auth")
                .then()
                .statusCode(200)
                .extract().as(AuthResponse.class);

        System.out.println("Токен: '"+resp.getToken()+"'");
        assertThat(resp.getToken()).isNull();

    }


}
