package com.moviebooking.moviebooking.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResponse {
    private String bookingResponse;

    public String getBookingResponse() {
        return bookingResponse;
    }

    public void setBookingResponse(String bookingResponse) {
        this.bookingResponse = bookingResponse;
    }
}
