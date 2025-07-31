package com.booking.hotel_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.hotel_service.constant.HotelConstant;
import com.booking.hotel_service.dto.HotelDto;
import com.booking.hotel_service.service.HotelService;

@ComponentScan
@RestController
@CrossOrigin
@RequestMapping(value = HotelConstant.HOTEL_CONTEXT_PATH)
public class HotelController {
	
	
	@Autowired
	private HotelService hotelService  ;
	
	@PostMapping("")
	public ResponseEntity<Object> addHotel(@RequestBody HotelDto require) throws Exception {
		
		hotelService.createHotel(require);
		return ResponseEntity.ok("OK");
	}

}
