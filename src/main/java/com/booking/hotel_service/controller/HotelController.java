package com.booking.hotel_service.controller;

import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.common.Resource;
import com.booking.hotel_service.constant.HotelConstant;
import com.booking.hotel_service.dto.HotelDto;
import com.booking.hotel_service.dto.HotelSearchRequest;
import com.booking.hotel_service.entity.Hotel;
import com.booking.hotel_service.exception.ResourceNotFoundException;
import com.booking.hotel_service.resource.HotelResourceBuilder;
import com.booking.hotel_service.service.HotelService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
@ComponentScan
@RestController
@CrossOrigin
@RequestMapping(value = HotelConstant.HOTEL_CONTEXT_PATH)
public class HotelController {
	
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private HotelResourceBuilder hotelResourceBuilder;
	
	@PostMapping("")
	public ResponseEntity<Object> addHotel(@Valid @RequestBody HotelDto require) throws Exception {
		
		hotelService.createHotel(require);
		return ResponseEntity.ok("OK");
	}
	
   
    @PostMapping("/search")
    public ResponseEntity<List<HotelDto>> searchHotels(@RequestBody HotelSearchRequest request) {
        List<Hotel> hotels = hotelService.searchHotels(request);
        List<HotelDto> dtos = hotels.stream()
        		.map(hotel -> modelMapper.map(hotel, HotelDto.class))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(dtos);
    }

    @GetMapping(value = "/{id}")
	public ResponseEntity<Object> getHotelById(@PathVariable("id") String id) throws Exception {
		Hotel hotel = hotelService.getHotelById(id);
		Resource<Map<String, Object>> resource = hotelResourceBuilder.getHotelInstanceResource(hotel);
		return ResponseEntity.ok(resource);
	}
    
    @GetMapping
    public ResponseEntity<List<HotelDto>> getAllHotels() {
        List<HotelDto> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }
    
    @PostMapping("/batch")
    public ResponseEntity<?> insertListHotel(@RequestBody List<HotelDto> hotelDtoList) {
        try {
            List<HotelDto> savedHotels = hotelService.saveAll(hotelDtoList);
            return ResponseEntity.ok(savedHotels);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi lưu danh sách khách sạn");
        }
    }
    
	@PutMapping(value = "/{id}")
	public ResponseEntity<HotelDto> updateProvice(@RequestBody HotelDto model, @PathVariable("id") UUID id){
		HotelDto updatedHotel = hotelService.updateHotel(id.toString(),model);
		return ResponseEntity.ok(updatedHotel);
	}

}
