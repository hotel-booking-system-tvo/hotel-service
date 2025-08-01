package com.booking.hotel_service.service;

import java.util.List;

import com.booking.hotel_service.dto.HotelDto;
import com.booking.hotel_service.dto.HotelSearchRequest;
import com.booking.hotel_service.entity.Hotel;

public interface HotelService {
	HotelDto createHotel(HotelDto require) throws Exception;
	List<Hotel> searchHotels(HotelSearchRequest request);
	List<HotelDto> getAllHotels();
	List<HotelDto> saveAll(List<HotelDto> hotels);
}
