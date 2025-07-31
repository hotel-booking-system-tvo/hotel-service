package com.booking.hotel_service.service;

import com.booking.hotel_service.dto.HotelDto;

public interface HotelService {
	HotelDto createHotel(HotelDto require) throws Exception;
}
