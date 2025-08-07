package com.booking.hotel_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.hotel_service.dto.RoomDto;
import com.booking.hotel_service.dto.RoomSearchRequest;
import com.booking.hotel_service.entity.Hotel;
import com.booking.hotel_service.entity.Room;
import com.booking.hotel_service.exception.ResourceNotFoundException;
import com.booking.hotel_service.projection.RoomView;
import com.booking.hotel_service.repository.HotelRepository;
import com.booking.hotel_service.repository.RoomRepository;

import jakarta.persistence.criteria.Predicate;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
    private  RoomRepository roomRepository;
	
	@Autowired
    private  HotelRepository hotelRepository;
	

	@Override
	public Room createRoom(RoomDto require) throws Exception {
		
		Hotel hotel = hotelRepository.findById(require.getHotelId())
		        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
		
		Room room = modelMapper.map(require, Room.class);
		room.setHotel(hotel);
		
		Room saveRoom = roomRepository.save(room);
		
		return saveRoom;
	}
	
	@Override
	public Page<Room> searchRooms(Pageable pageable,RoomSearchRequest request) {
	    return roomRepository.findAll((root, query, cb) -> {
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
	public Page<Room> getAllRooms(Pageable pageable) {
		Page<Room> hotels = roomRepository.findAll(pageable);

	    return hotels;
	}
	
	@Override
	public List<Room> saveAll(List<RoomDto> rooms) {
	    List<Room> roomEntities = rooms.stream()
	        .map(dto ->modelMapper.map(dto, Room.class))
	        .collect(Collectors.toList());

	    List<Room> savedEntities = roomRepository.saveAll(roomEntities);

	    return savedEntities;
	}

	@Override
	public Room updateRoom(RoomDto update) {
		
		Room existingRoom = roomRepository.findById(update.getHotelId())
		        .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + update.getHotelId()));
		
	    existingRoom.setName(update.getName());
	    existingRoom.setDescription(update.getDescription());
	    existingRoom.setRoomType(update.getRoomType());
        existingRoom.setCapacity(update.getCapacity());
        existingRoom.setBedcount(update.getBedcount());
        existingRoom.setPrice(update.getPrice());
        existingRoom.setIsAvailable(update.getIsAvailable());
        existingRoom.setStatus(update.getStatus());
		
		Room saveRoom = roomRepository.save(existingRoom);
		return saveRoom;
	}
	
	@Override
	public Room getRoomById(String id) {
		
		Room room = roomRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
		return room;
	}
	@Override
	public Boolean softDeleteRoom(String id) {
		Room room = roomRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));

		if (Boolean.TRUE.equals(room.getDeleted())) {
	        return false;
	    }
	    
		room.setDeleted(true);
	    roomRepository.save(room);
	    return true ;
	}
	@Override
	public Boolean restoreRoom(String id) {
		Room room = roomRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
		if (Boolean.FALSE.equals(room.getDeleted())) {
			return false;
		}
		room.setDeleted(false);
		roomRepository.save(room);
		return true ;
	}
	@Override
	public Page<RoomView> getRoomsByHotelId(String hotelId, Pageable pageable) {
	    return roomRepository.findAllProjectedByHotelId(hotelId, pageable);
	}
}
