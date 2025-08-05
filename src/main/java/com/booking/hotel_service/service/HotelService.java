package com.booking.hotel_service.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.booking.hotel_service.dto.HotelDto;
import com.booking.hotel_service.dto.HotelSearchRequest;
import com.booking.hotel_service.entity.Hotel;

public interface HotelService {
	Hotel createHotel(HotelDto require) throws Exception;
	Hotel getHotelById(String id);
	Page<Hotel> searchHotels(Pageable pageable,HotelSearchRequest request);
	Page<Hotel> getAllHotels(Pageable pageable);
	List<Hotel> saveAll(List<HotelDto> hotels);
	Hotel updateHotel(String id,HotelDto update);
}
