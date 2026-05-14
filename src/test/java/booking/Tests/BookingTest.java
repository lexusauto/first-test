package booking.Tests;

import booking.config.BaseApiTest;
import booking.config.BookingConfig;
import booking.dto.AuthResponse;
import booking.dto.BookingApiClient;
import booking.dto.CreateBookingDTO;
import booking.dto.CreateBookingResponse;
import booking.steps.BookingSteps;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static booking.config.BookingApiConfig.getBookingConfig;
import static booking.steps.BookingSteps.buildBookingRequest;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingTest extends BaseApiTest {

    private static final Faker faker = new Faker();
    private static final BookingConfig config = getBookingConfig();

    private final BookingApiClient bookingClient = new BookingApiClient();

    @Test
    @DisplayName("Успешная авторизация")
    void authTest() {

        Response resp = bookingClient.auth(config.username(), config.password());

        assertThat(resp.statusCode()).isEqualTo(200);
        assertThat(resp.as(AuthResponse.class).getToken()).isNotNull();

    }

    @Test
    @DisplayName("Успешный CreateBooking")
    void createBookingTest() {

        CreateBookingDTO expectedBody = buildBookingRequest();
        Response response = bookingClient.createBooking(expectedBody);
        assertThat(response.statusCode()).isEqualTo(200);

        CreateBookingResponse actualBody = response.as(CreateBookingResponse.class);
        assertThat(actualBody.getBookingid()).isNotNull();
        BookingSteps.bookingsShouldBeEqual(expectedBody, actualBody.getBooking());

    }

    @Test
    void updateBookingTest() {

        /*String token = bookingClient.auth(USER, PASSWORD)
                .as(AuthResponse.class)
                .getToken();*/

        Response createResp = bookingClient
                .createBooking(buildBookingRequest());
                assertThat(createResp.getStatusCode()).isEqualTo(200);

        CreateBookingDTO bookingReq = buildBookingRequest();
        Response updateResp = bookingClient
                .updateBooking(bookingReq, createResp.as(CreateBookingResponse.class).getBookingid());
                assertThat(updateResp.getStatusCode()).isEqualTo(200);

        CreateBookingDTO updateBookingDTO = updateResp.as(CreateBookingDTO.class);
        BookingSteps.bookingsShouldBeEqual(bookingReq, updateBookingDTO);

    }

    @Test
    void partialUpdateBookingTest() {

        Response createResp = bookingClient
                .createBooking(buildBookingRequest());
        assertThat(createResp.getStatusCode()).isEqualTo(200);

        CreateBookingDTO bookingReq = new CreateBookingDTO(faker.football().players(),faker.number().numberBetween(10001, 12000),"2026-01-02");

        Response updateResp = bookingClient
                .partialUpdateBooking(bookingReq, createResp.as(CreateBookingResponse.class).getBookingid());
        assertThat(updateResp.getStatusCode()).isEqualTo(200);

        CreateBookingDTO updateBookingDTO = updateResp.as(CreateBookingDTO.class);
        assertThat(bookingReq.getFirstname()).isEqualTo(updateBookingDTO.getFirstname());
        assertThat(bookingReq.getTotalprice()).isEqualTo(updateBookingDTO.getTotalprice());
        assertThat(bookingReq.getBookingdates().getCheckin()).isEqualTo(updateBookingDTO.getBookingdates().getCheckin());

    }

    @Test
    void deleteBookingTest() {
        Response createResp = bookingClient
                .createBooking(buildBookingRequest());
        assertThat(createResp.getStatusCode()).isEqualTo(200);

        Integer bookingId = createResp.as(CreateBookingResponse.class).getBookingid();
        Response deleteResp = bookingClient
                .deleteBooking(createResp.as(CreateBookingResponse.class).getBookingid());
        assertThat(deleteResp.getStatusCode()).isEqualTo(201);

        Response getResp = bookingClient
                .getBooking(bookingId);
        assertThat(getResp.getStatusCode()).isEqualTo(404);

    }

}

