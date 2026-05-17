package booking.Tests;

import booking.config.BaseApiTest;
import booking.config.BookingConfig;
import booking.dto.CreateBookingDTO;
import booking.dto.CreateBookingResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static booking.config.BookingApiConfig.getBookingConfig;
import static booking.steps.BookingSteps.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateBookingNegativeTest extends BaseApiTest {

    private static final BookingConfig config = getBookingConfig();

    @ParameterizedTest
    @CsvSource({"firstname, Джабраилов", "lastname, Игорь"})
    @DisplayName("Негативные тесты на создание бронирования. Без обязательного поля firstname и lastname")
        //От нормального API ожидаю 400 Bad Request
    void bookingTestWithoutFirstName(String field, String setValue) {

        CreateBookingDTO request = buildBookingRequestWithoutFields(field, setValue);

        String response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .post(config.bookingUrl()+"/booking")
                .then()
                .statusCode(500)
                .extract()
                .asString();

        System.out.println("Ответ сервера: " + response);
        assertThat(response).contains("Internal Server Error");

    }

    @Test
    @DisplayName("Отрицательная цена totalprice")
        //От нормального API ожидаю 400 Bad Request
    void negativeTotalPriceBookingTest() {

        CreateBookingResponse response = given()
                .contentType(ContentType.JSON)
                .body(buildBookingRequestNegativeTotalPrice())
                .post(config.bookingUrl()+"/booking")
                .then()
                .statusCode(200)
                .extract().as(CreateBookingResponse.class);

        assertThat(response.getBooking().getTotalprice()).isEqualTo(-1500);

    }

    @Test
    @DisplayName("Даты в неверном формате")
        //От нормального API ожидаю 400 Bad Request
    void invalidCheckinBookingTest() {

        CreateBookingResponse response = given()
                .contentType(ContentType.JSON)
                .body(buildBookingRequestInvalidCheckin())
                .post(config.bookingUrl()+"/booking")
                .then()
                .statusCode(200)
                .extract().as(CreateBookingResponse.class);

        assertThat(response.getBooking().getBookingdates().getCheckin()).isNotNull();

    }

    @Test
    @DisplayName("Дата выезда раньше даты заезда")
        //От нормального API ожидаю 400 Bad Request
    void unexpectedCheckoutDataBookingTest() {

        CreateBookingResponse response = given()
                .contentType(ContentType.JSON)
                .body(buildBookingRequestUnexpectedData())
                .post(config.bookingUrl()+"/booking")
                .then()
                .statusCode(200)
                .extract().as(CreateBookingResponse.class);

        assertThat(response.getBooking().getBookingdates().getCheckin()).isNotNull();
        assertThat(response.getBooking().getBookingdates().getCheckout()).isNotNull();
        System.out.println("Даты: "+response.getBooking().getBookingdates().getCheckin()+", "+response.getBooking().getBookingdates().getCheckout());

    }

    @Test
    @DisplayName("Пустое body booking")
        //От нормального API ожидаю 400 Bad Request
    void emptyBodyBookingTest() {

        String response = given()
                .contentType(ContentType.JSON)
                .post(config.bookingUrl()+"/booking")
                .then()
                .statusCode(500)
                .extract()
                .asString();

        System.out.println("Ответ сервера: " + response);
        assertThat(response).contains("Internal Server Error");

    }
}
