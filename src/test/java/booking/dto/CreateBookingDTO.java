package booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    public static class BookingDates {
        private String checkin;
        private String checkout;
    }

    private String additionalneeds;

}
