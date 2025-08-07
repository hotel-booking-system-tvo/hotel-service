package com.booking.hotel_service.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.booking.common.HALConstants;
import com.booking.common.HALOptions;
import com.booking.common.Resource;
import com.booking.hotel_service.controller.HotelController;
import com.booking.hotel_service.controller.RoomController;
import com.booking.hotel_service.dto.RoomDto;
import com.booking.hotel_service.entity.Room;
import com.booking.hotel_service.projection.RoomView;

@Component
public class RoomResourceBuilder {
	
	@Autowired
    private ModelMapper modelMapper;
	

	public Resource<Map<String, Object>> getRoomInstanceResource(Room room) throws Exception {
	    
		RoomDto roomDto = modelMapper.map(room, RoomDto.class);
		roomDto.setHotelId(room.getHotel().getId());
		
		Resource<Map<String, Object>> resource = new Resource<>();
		Map<String, Object> model = new HashMap<>();
		model.put("model", roomDto);
	    resource.setModel(model);

	    Map<String, Object> links = new HashMap<>();
	    links.put(HALConstants.SELF, 
	        linkTo(methodOn(RoomController.class).getRoomById(room.getId())).withSelfRel());
	    resource.set_links(links);

	    HALOptions options = new HALOptions();
	    options.setLinks(new ArrayList<>());
	    options.setTitle("room_details");
	    options.setRequired(new ArrayList<>());
	    options.setProperties(new HashMap<>());
	    resource.set_options(options);

	    return resource;
	}
	
	public Resource<Map<String, Object>> getRoomViewInstanceResource(RoomView room) throws Exception {
	    
	    Resource<Map<String, Object>> resource = new Resource<>();
	    Map<String, Object> model = new HashMap<>();
	    
	    // Bỏ qua Dto, build map thủ công từ Projection
	    Map<String, Object> roomData = new HashMap<>();
	    roomData.put("id", room.getId());
	    roomData.put("name", room.getName());
	    roomData.put("description", room.getDescription());
	    roomData.put("roomType", room.getRoomType());
	    roomData.put("capacity", room.getCapacity());
	    roomData.put("bedcount", room.getBedcount());
	    roomData.put("price", room.getPrice());
	    roomData.put("isAvailable", room.getIsAvailable());
	    roomData.put("status", room.getStatus());

	    if (room.getHotel() != null) {
	        Map<String, Object> hotel = new HashMap<>();
	        hotel.put("id", room.getHotel().getId());
	        hotel.put("name", room.getHotel().getName());
	        hotel.put("address", room.getHotel().getAddress());
	        roomData.put("hotel", hotel);
	    }

	    model.put("model", roomData);
	    resource.setModel(model);

	    // Links
	    Map<String, Object> links = new HashMap<>();
	    links.put(HALConstants.SELF,
	        linkTo(methodOn(RoomController.class).getRoomById(room.getId())).withSelfRel());
	    
	    if (room.getHotel() != null) {
	        links.put("hotel", 
	            linkTo(methodOn(HotelController.class).getHotelById(room.getHotel().getId())).withRel("hotel"));
	        links.put("hotel-rooms", 
	            linkTo(methodOn(RoomController.class).getRoomsByHotelId(null, room.getHotel().getId())).withRel("hotel-rooms"));
	    }

	    resource.set_links(links);

	    // Options
	    HALOptions options = new HALOptions();
	    options.setLinks(new ArrayList<>());
	    options.setTitle("room_details");
	    options.setRequired(new ArrayList<>());
	    options.setProperties(new HashMap<>());
	    resource.set_options(options);

	    return resource;
	}
}
