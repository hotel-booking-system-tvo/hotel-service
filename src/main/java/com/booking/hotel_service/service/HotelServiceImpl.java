package com.booking.hotel_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.hotel_service.dto.HotelDto;
import com.booking.hotel_service.dto.HotelSearchRequest;
import com.booking.hotel_service.entity.Hotel;
import com.booking.hotel_service.exception.ResourceNotFoundException;
import com.booking.hotel_service.repository.HotelRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
    private  HotelRepository hotelRepository;
	

	@Override
	public Hotel createHotel(HotelDto require) throws Exception {
		
		Hotel hotel = modelMapper.map(require, Hotel.class);
		
		Hotel saveHotel = hotelRepository.save(hotel);
		
		return saveHotel;
	}
	
	@Override
	public Page<Hotel> searchHotels(Pageable pageable,HotelSearchRequest request) {
	    return hotelRepository.findAll((root, query, cb) -> {
	        List<Predicate> predicates = new ArrayList<>();

	        if (request.getName() != null) {
	            predicates.add(cb.like(cb.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%"));
	        }

	        if (request.getAddress() != null) {
	            predicates.add(cb.like(cb.lower(root.get("address")), "%" + request.getAddress().toLowerCase() + "%"));
	        }

	        if (request.getMinPrice() != null) {
	            predicates.add(cb.greaterThanOrEqualTo(root.get("minPrice"), request.getMinPrice()));
	        }

	        if (request.getMaxPrice() != null) {
	            predicates.add(cb.lessThanOrEqualTo(root.get("maxPrice"), request.getMaxPrice()));
	        }

	        return cb.and(predicates.toArray(new Predicate[0]));
	    },pageable);
	    
	    
	}
	
	@Override
	public Page<Hotel> getAllHotels(Pageable pageable) {
		Page<Hotel> hotels = hotelRepository.findAll(pageable);
//	    return hotels.stream()
//	                 .map(hotel -> modelMapper.map(hotel, HotelDto.class))
//	                 .collect(Collectors.toList());
	    return hotels;
	}
	
	@Override
	public List<Hotel> saveAll(List<HotelDto> hotels) {
	    List<Hotel> hotelEntities = hotels.stream()
	        .map(dto ->modelMapper.map(dto, Hotel.class))
	        .collect(Collectors.toList());

	    List<Hotel> savedEntities = hotelRepository.saveAll(hotelEntities);
//
//	    return savedEntities.stream()
//	        .map(entity -> modelMapper.map(entity, HotelDto.class))
//	        .collect(Collectors.toList());
	    return savedEntities;
	}

	@Override
	public Hotel updateHotel(String id,HotelDto update) {
		
		Hotel existingHotel = hotelRepository.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
		
	    existingHotel.setName(update.getName());
	    existingHotel.setAddress(update.getAddress());
	    existingHotel.setDescription(update.getDescription());
	    existingHotel.setMinPrice(update.getMinPrice());
	    existingHotel.setMaxPrice(update.getMaxPrice());
	    existingHotel.setOpenTime(update.getOpenTime());
	    existingHotel.setCloseTime(update.getCloseTime());
		
		Hotel saveHotel = hotelRepository.save(existingHotel);
//		return modelMapper.map(saveHotel, HotelDto.class);
		return saveHotel;
	}
	
	@Override
	public Hotel getHotelById(String id) {
		
		Hotel hotel = hotelRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
		return hotel;
	}
	@Override
	public Boolean softDeleteHotel(String id) {
		Hotel hotel = hotelRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));

		if (Boolean.TRUE.equals(hotel.getDeleted())) {
	        return false;
	    }
	    
	    hotel.setDeleted(true);
	    hotelRepository.save(hotel);
	    return true ;
	}
	@Override
	public Boolean restoreHotel(String id) {
		Hotel hotel = hotelRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
		if (Boolean.FALSE.equals(hotel.getDeleted())) {
			return false;
		}
		hotel.setDeleted(false);
		hotelRepository.save(hotel);
		return true ;
	}
}
