package com.booking.hotel_service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RoomSearchRequest {

    private String name;
    private String address;
    private Integer minPrice;
    private Integer maxPrice;
    private String roomType; // ENUM: SINGLE, DOUBLE, SUITE, etc.
    private Integer capacity;
    private Integer bedcount;
    private BigDecimal price;
    private Boolean isAvailable = true;
    private String status;
}
