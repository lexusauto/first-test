package booking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateBookingDTO {
    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private BookingDates bookingdates;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BookingDates {
        private String checkin;
        private String checkout;
    }

    private String additionalneeds;

    public CreateBookingDTO(String firstName, Integer totalPrice, String checkin) {
        this.firstname = firstName;
        this.totalprice = totalPrice;
        this.bookingdates = new BookingDates();
        this.bookingdates.checkin = checkin;
    }
}
