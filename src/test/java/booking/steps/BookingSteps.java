package booking.steps;

import booking.dto.BookingApiClient;
import booking.dto.BookingId;
import booking.dto.CreateBookingDTO;
import booking.dto.CreateBookingResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BookingSteps {

    private static final Faker faker = new Faker();
    private final BookingApiClient bookingClient = new BookingApiClient();

    public CreateBookingResponse createBooking() {
        return createBooking(randomBooking());
    }

    public CreateBookingResponse createBooking(CreateBookingDTO booking) {
        Response createResp = bookingClient
                .createBooking(booking);
        assertThat(createResp.getStatusCode()).isEqualTo(200);

        return createResp.as(CreateBookingResponse.class);
    }

    @Step("Сгенерировать {bookingQuantity} бронирований с фамилией {lastName}")
    public List<Integer> generateBookings(int bookingQuantity, String lastName) {
        List<Integer> bookingIds = new ArrayList<>();
        for (int i = 0; i< bookingQuantity; i++) {
            CreateBookingDTO createBookingDTO = randomBooking();
            createBookingDTO.setLastname(lastName);
            Integer bookingId = createBooking(createBookingDTO).getBookingid();

            bookingIds.add(bookingId);

        }
        return bookingIds;
    }

    @Step("Проверить соответствие всех полей в ответе")
    public static void bookingsShouldBeEqual(CreateBookingDTO expected, CreateBookingDTO actual) {
        assertAll(
                () -> assertThat(actual.getTotalprice())
                        .isEqualTo(expected.getTotalprice()),
                () -> assertThat(actual.getDepositpaid())
                        .isEqualTo(expected.getDepositpaid()),
                () -> assertThat(actual.getAdditionalneeds())
                        .isEqualTo(expected.getAdditionalneeds()),
                () -> assertThat(actual.getBookingdates())
                        .isNotNull(),
                () -> assertThat(actual.getBookingdates().getCheckin())
                        .isEqualTo(expected.getBookingdates().getCheckin()),
                () -> assertThat(actual.getBookingdates().getCheckout())
                        .isEqualTo(expected.getBookingdates().getCheckout())
        );
    }

    public static void bookingListShouldBeValid(List<BookingId> bookings, List<Integer> expectedBookingIds, Integer bookingQuantity) {
        assertThat(bookings)
                .hasSize(bookingQuantity)
                .doesNotHaveDuplicates()
                .doesNotContainNull()
                .extracting(booking -> booking.bookingid())
                .containsExactlyInAnyOrderElementsOf(expectedBookingIds);
    }



    public static CreateBookingDTO randomBooking() {
        return CreateBookingDTO.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .totalprice(faker.number().numberBetween(1000, 10000))
                .depositpaid(faker.bool().bool())
                .bookingdates(CreateBookingDTO.BookingDates.builder()
                        .checkin("2026-01-01")
                        .checkout("2027-01-01")
                        .build())
                .additionalneeds(faker.videoGame().title())
                .build();
    }

    public static CreateBookingDTO buildBookingRequestWithoutFields(String field, String setValue) {
        CreateBookingDTO.CreateBookingDTOBuilder builder = CreateBookingDTO.builder()
                .totalprice(1500)
                .depositpaid(false)
                .bookingdates(CreateBookingDTO.BookingDates.builder()
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

    public static CreateBookingDTO buildBookingRequestNegativeTotalPrice() {
        return CreateBookingDTO.builder()
                .firstname("Игорь")
                .lastname("Джабраилов")
                .totalprice(-1500)
                .depositpaid(false)
                .bookingdates(CreateBookingDTO.BookingDates.builder()
                        .checkin("2026-01-01")
                        .checkout("2027-01-01")
                        .build())
                .additionalneeds("newspaper")
                .build();
    }

    public static CreateBookingDTO buildBookingRequestInvalidCheckin() {
        return CreateBookingDTO.builder()
                .firstname("Игорь")
                .lastname("Джабраилов")
                .totalprice(1500)
                .depositpaid(false)
                .bookingdates(CreateBookingDTO.BookingDates.builder()
                        .checkin("79115214321")
                        .checkout("2027-01-01")
                        .build())
                .additionalneeds("newspaper")
                .build();
    }

    public static CreateBookingDTO buildBookingRequestUnexpectedData() {
        return CreateBookingDTO.builder()
                .firstname("Игорь")
                .lastname("Джабраилов")
                .totalprice(1500)
                .depositpaid(false)
                .bookingdates(CreateBookingDTO.BookingDates.builder()
                        .checkin("2027-01-01")
                        .checkout("2026-01-01")
                        .build())
                .additionalneeds("newspaper")
                .build();
    }
}
