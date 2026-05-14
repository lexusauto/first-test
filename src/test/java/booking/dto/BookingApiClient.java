package booking.dto;

import booking.config.BookingConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static booking.config.BookingApiConfig.getBookingConfig;
import static io.restassured.RestAssured.given;

public class BookingApiClient {

    private static final BookingConfig config = getBookingConfig();

    private RequestSpecification spec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON).build();

    public Response auth(String user, String password) {
        return given(spec)
                .body(new AuthRequest(user, password))

                .post(config.bookingUrl() + "/auth")
                .then()
                .extract().response();
    }

    public Response createBooking(CreateBookingDTO createBookingDTO) {
        return given(spec)
                .body(createBookingDTO)
                .post(config.bookingUrl() + "/booking")
                .then()
                .extract().response();
    }

    public Response partialUpdateBooking
            (CreateBookingDTO createBookingDTO, Integer id) {
        return given(spec)
                .cookie("token", getToken())
                .body(createBookingDTO)
                .pathParam("BOOKING_ID", id)
                .patch(config.bookingUrl() + "/booking/{BOOKING_ID}")
                .then()
                .extract().response();
    }

    public Response getBooking (Integer id) {
        return given()
                .pathParam("BOOKING_ID", id)
                .get(config.bookingUrl()+ "/booking/{BOOKING_ID}")
                .then()
                .extract().response();

    }

    public Response deleteBooking (Integer id) {
      return given()
              .cookie("token", getToken())
              .pathParam("BOOKING_ID", id)
              .delete(config.bookingUrl()+ "/booking/{BOOKING_ID}")
              .then()
              .extract().response();

    }



    public Response updateBooking(CreateBookingDTO createBookingDTO, Integer id) {
        return given(spec)
                .cookie("token", getToken())
                .body(createBookingDTO)
                .pathParam("BOOKING_ID", id)
                .put(config.bookingUrl() + "/booking/{BOOKING_ID}")
                .then()
                .extract().response();
    }

    private String getToken() {
        return auth(config.username(), config.password()).as(AuthResponse.class).getToken();
    }
}
