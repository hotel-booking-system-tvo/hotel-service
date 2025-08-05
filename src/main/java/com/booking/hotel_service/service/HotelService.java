package com.booking.hotel_service.service;

import java.util.List;

import com.booking.hotel_service.dto.HotelDto;
import com.booking.hotel_service.dto.HotelSearchRequest;
import com.booking.hotel_service.entity.Hotel;

public interface HotelService {
	Hotel createHotel(HotelDto require) throws Exception;
	Hotel getHotelById(String id);
	List<Hotel> searchHotels(HotelSearchRequest request);
	List<Hotel> getAllHotels();
	List<Hotel> saveAll(List<HotelDto> hotels);
	Hotel updateHotel(String id,HotelDto update);
}
