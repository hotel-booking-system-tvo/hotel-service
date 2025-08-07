package com.booking.hotel_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomDto {
	
	@NotBlank(message = "Tên không được để trống")
	private String name;
	private String description;
	
    private String roomType; // ENUM: SINGLE, DOUBLE, SUITE, etc.
    private Integer capacity;
    private Integer bedcount;
    private BigDecimal price;
    private Boolean isAvailable = true;
    private String status;
    private String hotelId;
    private String id;
	
	private String createdBy;
	private String updatedBy;
}
