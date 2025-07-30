package com.booking.hotel_service.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class HotelDto {
	private String name;
	private String address;
	private String description;
	private LocalDate createdDate;
	private LocalDate openTime;
	private LocalDate closeTime;
	private int minPrice;
	private int maxPrice;

}
