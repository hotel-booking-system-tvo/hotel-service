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
import com.booking.hotel_service.controller.RoomController;
import com.booking.hotel_service.dto.RoomDto;
import com.booking.hotel_service.entity.Room;

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
}
