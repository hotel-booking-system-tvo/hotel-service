package com.booking.hotel_service.dto;

import lombok.Data;

@Data
public class HotelSearchRequest {

    private String name;
    private String address;
    private Integer minPrice;
    private Integer maxPrice;
}
