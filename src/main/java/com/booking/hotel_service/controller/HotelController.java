package com.booking.hotel_service.controller;

import org.apache.catalina.connector.Response;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.hotel_service.constant.HotelConstant;
import com.booking.hotel_service.dto.HotelDto;

@ComponentScan
@RestController
@CrossOrigin
@RequestMapping(value = HotelConstant.HOTEL_CONTEXT_PATH)
public class HotelController {
	@PostMapping("")
	public ResponseEntity<Object> addHotel(@RequestBody HotelDto hotelDto) throws Exception {
		return ResponseEntity.ok("OK");
	}

}
