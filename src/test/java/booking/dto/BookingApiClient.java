package booking.dto;

import booking.config.BookingConfig;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static booking.config.BookingApiConfig.getBookingConfig;
import static io.restassured.RestAssured.given;

public class BookingApiClient {

    private static final BookingConfig config = getBookingConfig();

    private RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri(config.bookingUrl())
            .setContentType(ContentType.JSON).build();

    @Step("Выполнить запрос POST /auth")
    public Response auth(String user, String password) {
        return given(spec)
                .body(new AuthRequest(user, password))

                .post("/auth")
                .then()
                .extract().response();
    }

    @Step("Выполнить запрос POST /booking")
    public Response createBooking(CreateBookingDTO createBookingDTO) {
        return given(spec)
                .body(createBookingDTO)
                .post("/booking")
                .then()
                .extract().response();
    }

    @Step("Выполнить запрос PATCH /booking/{id}")
    public Response partialUpdateBooking
            (CreateBookingDTO createBookingDTO, Integer id) {
        return given(spec)
                .cookie("token", getToken())
                .body(createBookingDTO)
                .pathParam("BOOKING_ID", id)
                .patch("/booking/{BOOKING_ID}")
                .then()
                .extract().response();
    }

    @Step("Выполнить запрос GET /booking/{id}")
    public Response getBooking (Integer id) {
        return given(spec)
                .pathParam("BOOKING_ID", id)
                .get("/booking/{BOOKING_ID}")
                .then()
                .extract().response();

    }

    @Step("Выполнить запрос GET /booking?{queryParams}")
    public Response getBookings (Map<String, Object> queryParams) {
        return given(spec)
                .queryParams(queryParams)
                .log().params()
                .get("/booking")
                .then()
                .extract().response();

    }

    @Step("Выполнить запрос DELETE /booking/{id}")
    public Response deleteBooking (Integer id) {
      return given(spec)
              .cookie("token", getToken())
              .pathParam("BOOKING_ID", id)
              .delete("/booking/{BOOKING_ID}")
              .then()
              .extract().response();

    }




    @Step("Выполнить запрос PUT /booking/{id}")
    public Response updateBooking(CreateBookingDTO createBookingDTO, Integer id) {
        return given(spec)
                .cookie("token", getToken())
                .body(createBookingDTO)
                .pathParam("BOOKING_ID", id)
                .put("/booking/{BOOKING_ID}")
                .then()
                .extract().response();
    }

    private String getToken() {
        return auth(config.username(), config.password()).as(AuthResponse.class).getToken();
    }
}
