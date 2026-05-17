package booking.Tests;

import booking.config.BaseApiTest;
import booking.config.BookingConfig;
import booking.dto.*;
import booking.steps.BookingSteps;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static booking.config.BookingApiConfig.getBookingConfig;
import static booking.steps.BookingSteps.randomBooking;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingTest extends BaseApiTest {

    private static final Faker faker = new Faker();
    private static final BookingConfig config = getBookingConfig();

    private final BookingApiClient bookingClient = new BookingApiClient();
    private final BookingSteps bookingSteps = new BookingSteps();

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

        CreateBookingDTO expectedBody = randomBooking();
        Response response = bookingClient.createBooking(expectedBody);
        assertThat(response.statusCode()).isEqualTo(200);

        CreateBookingResponse actualBody = response.as(CreateBookingResponse.class);
        assertThat(actualBody.getBookingid()).isNotNull();
        BookingSteps.bookingsShouldBeEqual(expectedBody, actualBody.getBooking());

    }

    @Test
    @DisplayName("Полное обновление букинга")
    void updateBookingTest() {
        Integer bookingId = bookingSteps.createBooking().getBookingid();

        CreateBookingDTO bookingReq = randomBooking();
        Response updateResp = bookingClient
                .updateBooking(bookingReq, bookingId);
                assertThat(updateResp.getStatusCode()).isEqualTo(200);

        CreateBookingDTO updateBookingDTO = updateResp.as(CreateBookingDTO.class);
        BookingSteps.bookingsShouldBeEqual(bookingReq, updateBookingDTO);

    }

    @Test
    @DisplayName("Частичное обновление букинга")
    void partialUpdateBookingTest() {

        Integer bookingId = bookingSteps.createBooking().getBookingid();

        CreateBookingDTO bookingReq = new CreateBookingDTO(faker.football().players(),faker.number().numberBetween(10001, 12000),"2026-01-02");

        Response updateResp = bookingClient
                .partialUpdateBooking(bookingReq, bookingId);
        assertThat(updateResp.getStatusCode()).isEqualTo(200);

        CreateBookingDTO updateBookingDTO = updateResp.as(CreateBookingDTO.class);
        assertThat(bookingReq.getFirstname()).isEqualTo(updateBookingDTO.getFirstname());
        assertThat(bookingReq.getTotalprice()).isEqualTo(updateBookingDTO.getTotalprice());
        assertThat(bookingReq.getBookingdates().getCheckin()).isEqualTo(updateBookingDTO.getBookingdates().getCheckin());

    }

    @Test
    @DisplayName("Удаление букинга")
    void deleteBookingTest() {
        Integer bookingId = bookingSteps.createBooking().getBookingid();

        Response deleteResp = bookingClient
                .deleteBooking(bookingId);
        assertThat(deleteResp.getStatusCode()).isEqualTo(201);

        Response getResp = bookingClient
                .getBooking(bookingId);
        assertThat(getResp.getStatusCode()).isEqualTo(404);

    }

    @Test
    @DisplayName("Валидация наличия букинга")
    void getBookingTest() {
        CreateBookingResponse booking = bookingSteps.createBooking();

        Response createResp = bookingClient.getBooking(booking.getBookingid());
        assertThat(createResp.getStatusCode()).isEqualTo(200);

        BookingSteps.bookingsShouldBeEqual(booking.getBooking(), createResp.as(CreateBookingDTO.class));

    }

    @Test
    @DisplayName("Получение bookindid па параметру lastname")
    void getBookingsByLastName() {
        String lastName = faker.name().lastName();
        int bookingQuantity = 5;

        List<Integer> bookingIds = bookingSteps.generateBookings(bookingQuantity, lastName);

        Response resp = bookingClient.getBookings(Map.of("lastname", lastName));
        assertThat(resp.getStatusCode()).isEqualTo(200);

        List<BookingId> bookings =resp.as(new TypeRef<List<BookingId>>() {});

        BookingSteps.bookingListShouldBeValid(bookings,bookingIds, bookingQuantity);

    }

}

