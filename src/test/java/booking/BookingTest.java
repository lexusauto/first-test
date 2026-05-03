package booking;

import booking.dto.AuthRequest;
import booking.dto.AuthResponse;
import booking.dto.CreateBookingDTO;
import booking.dto.CreateBookingDTO.BookingDates;
import booking.dto.CreateBookingResponse;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingTest {

    private static final String BOOKING_URL = "https://restful-booker.herokuapp.com";

    @BeforeAll
    static void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    @DisplayName("Успешная авторизация")
    void authTest() {
        String user = "admin";
        String password = "password123";

        AuthResponse resp = given()
                .contentType(ContentType.JSON)
                .body(new AuthRequest(user, password))

                .post(BOOKING_URL + "/auth")
                .then()
                .statusCode(200)
                .extract().as(AuthResponse.class);

        assertThat(resp.getToken()).isNotNull();

    }

    @Test
    @DisplayName("Авторизация. Неверный пароль")
    void invalidPasswordTest() {
        String user = "admin";
        String password = "pass123";

        AuthResponse resp = given()
                .contentType(ContentType.JSON)
                .body(new AuthRequest(user, password))

                .post(BOOKING_URL + "/auth")
                .then()
                .statusCode(200)
                .extract().as(AuthResponse.class);

        assertThat(resp.getToken()).isNull();

    }

    @Test
    @DisplayName("Авторизация. Неверный логин")
    void invalidLoginTest() {
        String user = "adminus";
        String password = "password123";

        AuthResponse resp = given()
                .contentType(ContentType.JSON)
                .body(new AuthRequest(user, password))

                .post(BOOKING_URL + "/auth")
                .then()
                .statusCode(200)
                .extract().as(AuthResponse.class);

        assertThat(resp.getToken()).isNull();

    }

    @Test
    @DisplayName("Авторизация. Пустой пароль")
    void emptyPasswordTest() {
        String user = "admin";
        String password = "";

        AuthResponse resp = given()
                .contentType(ContentType.JSON)
                .body(new AuthRequest(user, password))

                .post(BOOKING_URL + "/auth")
                .then()
                .statusCode(200)
                .extract().as(AuthResponse.class);

        System.out.println("Токен: '"+resp.getToken()+"'");
        assertThat(resp.getToken()).isNull();

    }

    @Test
    @DisplayName("Авторизация. Пустой логин")
    void emptyLoginTest() {
        String user = "";
        String password = "password123";

        AuthResponse resp = given()
                .contentType(ContentType.JSON)
                .body(new AuthRequest(user, password))

                .post(BOOKING_URL + "/auth")
                .then()
                .statusCode(200)
                .extract().as(AuthResponse.class);

        System.out.println("Токен: '"+resp.getToken()+"'");
        assertThat(resp.getToken()).isNull();

    }

    @Test
    @DisplayName("Авторизация. Пустое body")
    void emptyBodyTest() {
        String user = "admin";
        String password = "password123";

        AuthResponse resp = given()
                .contentType(ContentType.JSON)
                .body("{}")

                .post(BOOKING_URL + "/auth")
                .then()
                .statusCode(200)
                .extract().as(AuthResponse.class);

        System.out.println("Токен: '"+resp.getToken()+"'");
        assertThat(resp.getToken()).isNull();

    }

    @Test
    @DisplayName("Авторизация. Без body вообще")
    void withoutBodyTest() {
        String user = "admin";
        String password = "password123";

        AuthResponse resp = given()
                .contentType(ContentType.JSON)

                .post(BOOKING_URL + "/auth")
                .then()
                .statusCode(200)
                .extract().as(AuthResponse.class);

        System.out.println("Токен: '"+resp.getToken()+"'");
        assertThat(resp.getToken()).isNull();

    }

    @Test
    @DisplayName("Успешный CreateBooking")
    void createBookingTest() {

        CreateBookingResponse response = given()
                .contentType(ContentType.JSON)
                .body(buildBookingRequest())
                .post(BOOKING_URL+"/booking")
                .then()
                .statusCode(200)
                .extract().as(CreateBookingResponse.class);

        assertThat(response.getBookingid()).isNotNull();
        assertThat(response.getBooking().getTotalprice()).isEqualTo(1500);
        assertThat(response.getBooking().getBookingdates().getCheckin()).isEqualTo("2026-01-01");
        assertThat(response.getBooking().getDepositpaid()).isFalse();

    }

    @ParameterizedTest
    @CsvSource({"firstname, Джабраилов", "lastname, Игорь"})
    @DisplayName("Негативные тесты на создание бронирования. Без обязательного поля firstname и lastname")
    //От нормального API ожидаю 400 Bad Request
    void bookingTestWithoutFirstName(String field, String setValue) {

        CreateBookingDTO request = buildBookingRequestWithoutFields(field, setValue);

        String response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .post(BOOKING_URL+"/booking")
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
                .post(BOOKING_URL+"/booking")
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
                .post(BOOKING_URL+"/booking")
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
                .post(BOOKING_URL+"/booking")
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
                .post(BOOKING_URL+"/booking")
                .then()
                .statusCode(500)
                .extract()
                .asString();

        System.out.println("Ответ сервера: " + response);
        assertThat(response).contains("Internal Server Error");

    }

    private static CreateBookingDTO bookingRequest() {
        CreateBookingDTO booking = new CreateBookingDTO();
        booking.setFirstname("Игорь");
        booking.setLastname("Джабраилов");
        booking.setTotalprice(1500);
        booking.setDepositpaid(false);
        booking.setBookingdates(new BookingDates("2026-01-01", "2027-01-01"));
        booking.setAdditionalneeds("newspaper");
        return booking;
    }

    private static CreateBookingDTO buildBookingRequest() {
        return CreateBookingDTO.builder()
                .firstname("Игорь")
                .lastname("Джабраилов")
                .totalprice(1500)
                .depositpaid(false)
                .bookingdates(BookingDates.builder()
                        .checkin("2026-01-01")
                        .checkout("2027-01-01")
                        .build())
                .additionalneeds("newspaper")
                .build();
    }

    private static CreateBookingDTO buildBookingRequestWithoutFields(String field, String setValue) {
        CreateBookingDTO.CreateBookingDTOBuilder builder = CreateBookingDTO.builder()
                .totalprice(1500)
                .depositpaid(false)
                .bookingdates(BookingDates.builder()
                        .checkin("2026-01-01")
                        .checkout("2027-01-01")
                        .build())
                .additionalneeds("newspaper");

        if(field.equals("firstname")) {
            builder.firstname(null);
            builder.lastname(setValue);
        } else if (field.equals("lastname")) {
            builder.firstname(setValue);
            builder.lastname(null);
        }

                return builder.build();
    }

    private static CreateBookingDTO buildBookingRequestNegativeTotalPrice() {
        return CreateBookingDTO.builder()
                .firstname("Игорь")
                .lastname("Джабраилов")
                .totalprice(-1500)
                .depositpaid(false)
                .bookingdates(BookingDates.builder()
                        .checkin("2026-01-01")
                        .checkout("2027-01-01")
                        .build())
                .additionalneeds("newspaper")
                .build();
    }

    private static CreateBookingDTO buildBookingRequestInvalidCheckin() {
        return CreateBookingDTO.builder()
                .firstname("Игорь")
                .lastname("Джабраилов")
                .totalprice(1500)
                .depositpaid(false)
                .bookingdates(BookingDates.builder()
                        .checkin("79115214321")
                        .checkout("2027-01-01")
                        .build())
                .additionalneeds("newspaper")
                .build();
    }

    private static CreateBookingDTO buildBookingRequestUnexpectedData() {
        return CreateBookingDTO.builder()
                .firstname("Игорь")
                .lastname("Джабраилов")
                .totalprice(1500)
                .depositpaid(false)
                .bookingdates(BookingDates.builder()
                        .checkin("2027-01-01")
                        .checkout("2026-01-01")
                        .build())
                .additionalneeds("newspaper")
                .build();
    }

}

