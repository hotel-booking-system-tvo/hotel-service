package com.booking.hotel_service.service;

import java.util.List;

import com.booking.hotel_service.dto.HotelDto;
import com.booking.hotel_service.entity.Hotel;

public interface HotelService {
	List<Hotel> createHotel(HotelDto require) throws Exception;
}
