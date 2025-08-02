package com.booking.hotel_service.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.booking.common.HALConstants;
import com.booking.common.HALOptions;
import com.booking.common.Resource;
import com.booking.hotel_service.controller.HotelController;
import com.booking.hotel_service.entity.Hotel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class HotelResourceBuilder {

	public Resource<Map<String, Object>> getHotelInstanceResource(Hotel hotel) throws Exception {
	    Resource<Map<String, Object>> resource = new Resource<>();
	    resource.setModel(hotel);

	    Map<String, Object> links = new HashMap<>();
	    links.put(HALConstants.SELF, 
	        linkTo(methodOn(HotelController.class).getHotelById(hotel.getId())).withSelfRel());
	    resource.set_links(links);

	    HALOptions options = new HALOptions();
	    options.setLinks(new ArrayList<>());
	    options.setTitle("hotel_details");
	    options.setRequired(new ArrayList<>());
	    options.setProperties(new HashMap<>());
	    resource.set_options(options);

	    return resource;
	}
}
