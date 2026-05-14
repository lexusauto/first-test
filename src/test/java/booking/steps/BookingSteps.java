package booking.steps;

import booking.dto.CreateBookingDTO;
import io.qameta.allure.Step;
import net.datafaker.Faker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BookingSteps {

    private static final Faker faker = new Faker();

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



    public static CreateBookingDTO buildBookingRequest() {
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
