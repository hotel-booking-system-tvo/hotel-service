package com.booking.hotel_service.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.booking.hotel_service.dto.RoomDto;
import com.booking.hotel_service.dto.RoomSearchRequest;
import com.booking.hotel_service.entity.Room;

public interface RoomService {
	Room createRoom(String hotelId,RoomDto require) throws Exception;
	Room getRoomById(String id);
	Page<Room> searchRooms(Pageable pageable,RoomSearchRequest request);
	Page<Room> getAllRooms(Pageable pageable);
	List<Room> saveAll(List<RoomDto> rooms);
	Room updateRoom(String id,RoomDto update);
	Boolean softDeleteRoom(String hotelId);
	Boolean restoreRoom(String id);
}
