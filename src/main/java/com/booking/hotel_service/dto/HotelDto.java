package com.booking.hotel_service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HotelDto {
	
	@NotBlank(message = "Tên không được để trống")
	private String name;
	@NotBlank(message = "Địa chỉ không được để trống")
	private String address;
	private String description;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
	private LocalDateTime openTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
	private LocalDateTime closeTime;
	
	private int minPrice;
	private int maxPrice;
	
	private String createdBy;
	private String updatedBy;
}
