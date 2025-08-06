package com.booking.hotel_service.controller;

import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.common.Resource;
import com.booking.hotel_service.constant.RoomConstant;
import com.booking.hotel_service.dto.RoomDto;
import com.booking.hotel_service.entity.Room;
import com.booking.hotel_service.resource.RoomResourceBuilder;
import com.booking.hotel_service.service.RoomService;

import jakarta.validation.Valid;
@ComponentScan
@RestController
@CrossOrigin
@RequestMapping(value = RoomConstant.ROOM_CONTEXT_PATH)
public class RoomController {
	
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private RoomResourceBuilder roomResourceBuilder;
	
	@PostMapping("/{hotelId}/rooms")
	public ResponseEntity<Object> addRoom(@Valid @RequestBody RoomDto require, @PathVariable("hotelId") UUID id) throws Exception {
		
		Room room=  roomService.createRoom(id.toString(),require);
		Resource<Map<String, Object>> resource = roomResourceBuilder.getRoomInstanceResource(room);
		return ResponseEntity.ok(resource);
	}
	
    @GetMapping(value = "/{id}")
	public ResponseEntity<Object> getRoomById(@PathVariable("id") String id) throws Exception {
		Room room = roomService.getRoomById(id);
		Resource<Map<String, Object>> resource = roomResourceBuilder.getRoomInstanceResource(room);
		return ResponseEntity.ok(resource);
	}
 
}
