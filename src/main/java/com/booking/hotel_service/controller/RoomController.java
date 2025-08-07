package com.booking.hotel_service.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.common.Resource;
import com.booking.hotel_service.constant.RoomConstant;
import com.booking.hotel_service.dto.HotelDto;
import com.booking.hotel_service.dto.RoomDto;
import com.booking.hotel_service.entity.Hotel;
import com.booking.hotel_service.entity.Room;
import com.booking.hotel_service.exception.ResourceNotFoundException;
import com.booking.hotel_service.pageable.PagedModelBuilder;
import com.booking.hotel_service.projection.RoomView;
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
	
	@PostMapping("")
	public ResponseEntity<Object> addRoom(@Valid @RequestBody RoomDto require) throws Exception {
		
		 try {
		        Room room = roomService.createRoom(require);
		        Resource<Map<String, Object>> resource = roomResourceBuilder.getRoomInstanceResource(room);
		        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
		    } catch (ResourceNotFoundException ex) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
		    } catch (Exception ex) {
		        // log exception
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Lỗi hệ thống: " + ex.getMessage()));
		    }
	}
	
    @GetMapping(value = "/{id}")
	public ResponseEntity<Object> getRoomById(@PathVariable("id") String id) throws Exception {
		Room room = roomService.getRoomById(id);
		Resource<Map<String, Object>> resource = roomResourceBuilder.getRoomInstanceResource(room);
		return ResponseEntity.ok(resource);
	}
    
    @PutMapping("/{id}")
	public ResponseEntity<Object> updateRoom(@Valid @RequestBody RoomDto require) throws Exception {
		
		Room room=  roomService.updateRoom(require);
		Resource<Map<String, Object>> resource = roomResourceBuilder.getRoomInstanceResource(room);
		return ResponseEntity.ok(resource);
	}
    
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteRoom(@PathVariable("id") UUID id) {
		
		Boolean status =  roomService.softDeleteRoom(id.toString());
	    return ResponseEntity.ok(status); 
	}
	
	@PutMapping("/{id}/restore")
	public ResponseEntity<Boolean> restoreHotel(@PathVariable("id") UUID id) {
		Boolean status =  roomService.restoreRoom(id.toString());
	    return ResponseEntity.ok(status);
	}
	
    @PostMapping("/batch")
	public ResponseEntity<List<Resource<Map<String, Object>>>> insertListRoom(
			@RequestBody List<RoomDto> roomDtoList) {
		List<Resource<Map<String, Object>>> resources = roomService.saveAll(roomDtoList).stream().map(room -> {
			try {
				return roomResourceBuilder.getRoomInstanceResource(room);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList());
		return ResponseEntity.ok(resources);
	}
    
    @GetMapping
    public ResponseEntity<PagedModel<Resource<Map<String, Object>>>> getAllRooms(@PageableDefault(size = 5, sort = { "name", "minPrice" },direction = Sort.Direction.ASC) Pageable pageable ) throws Exception {
    	
    	 Page<Room> page = roomService.getAllRooms(pageable);
    	
    	List<Resource<Map<String, Object>>> resources  = page.getContent().stream()
    			.map(room -> {
    	            try {
    	                return roomResourceBuilder.getRoomInstanceResource(room);
    	            } catch (Exception e) {
    	                throw new RuntimeException(e);
    	            }
    	        })
    	        .collect(Collectors.toList());
    	
        PagedModel<Resource<Map<String, Object>>> pagedModel = PagedModelBuilder.build(page, resources);

        return ResponseEntity.ok(pagedModel);
    }
    
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<?> getRoomsByHotelId(@PageableDefault(size = 5, sort = { "name"},direction = Sort.Direction.ASC) Pageable pageable ,@PathVariable String hotelId) {
    	
        Page<RoomView> page = roomService.getRoomsByHotelId(hotelId, pageable);

        List<Resource<Map<String, Object>>> resources = page.getContent().stream()
                .map(roomView -> {
                    try {
                        return roomResourceBuilder.getRoomViewInstanceResource(roomView);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to build resource", e);
                    }
                })
                .collect(Collectors.toList());

        PagedModel<Resource<Map<String, Object>>> pagedModel = PagedModelBuilder.build(page, resources);
        return ResponseEntity.ok(pagedModel);
    }
 
}
