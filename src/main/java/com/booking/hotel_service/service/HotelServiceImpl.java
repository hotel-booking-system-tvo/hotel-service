package com.booking.hotel_service.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.hotel_service.dto.HotelDto;
import com.booking.hotel_service.entity.Hotel;
import com.booking.hotel_service.repository.HotelRepository;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
    private  HotelRepository hotelRepository;
	

	@Override
	public HotelDto createHotel(HotelDto require) throws Exception {
		
		Hotel hotel = modelMapper.map(require, Hotel.class);
		
		Hotel saveHotel = hotelRepository.save(hotel);
		
		return modelMapper.map(saveHotel, HotelDto.class);
	}

}
