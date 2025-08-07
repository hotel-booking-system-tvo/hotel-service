package com.booking.hotel_service.controller;

import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.data.domain.Sort;

import com.booking.common.Resource;
import com.booking.hotel_service.constant.HotelConstant;
import com.booking.hotel_service.dto.HotelDto;
import com.booking.hotel_service.dto.HotelSearchRequest;
import com.booking.hotel_service.entity.Hotel;
import com.booking.hotel_service.exception.ResourceNotFoundException;
import com.booking.hotel_service.pageable.PagedModelBuilder;
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
		
		Hotel hotel=  hotelService.createHotel(require);
		Resource<Map<String, Object>> resource = hotelResourceBuilder.getHotelInstanceResource(hotel);
		return ResponseEntity.ok(resource);
	}
	
   
    @PostMapping("/search")
    public ResponseEntity<PagedModel<Resource<Map<String, Object>>>> searchHotels(@PageableDefault(size = 5, sort = { "name", "minPrice" },direction = Sort.Direction.ASC) Pageable pageable ,@RequestBody HotelSearchRequest request) {

    	Page<Hotel> page =hotelService.searchHotels(pageable,request);
    	
    	List<Resource<Map<String, Object>>> resources  = page.getContent().stream()
    			.map(hotel -> {
    	            try {
    	                return hotelResourceBuilder.getHotelInstanceResource(hotel);
    	            } catch (Exception e) {
    	                throw new RuntimeException(e);
    	            }
    	        })
    	        .collect(Collectors.toList());
    	
        PagedModel<Resource<Map<String, Object>>> pagedModel = PagedModelBuilder.build(page, resources);

        return ResponseEntity.ok(pagedModel);

    }

    @GetMapping(value = "/{id}")
	public ResponseEntity<Object> getHotelById(@PathVariable("id") String id) throws Exception {
		Hotel hotel = hotelService.getHotelById(id);
		Resource<Map<String, Object>> resource = hotelResourceBuilder.getHotelInstanceResource(hotel);
		return ResponseEntity.ok(resource);
	}
    
    @GetMapping
    public ResponseEntity<PagedModel<Resource<Map<String, Object>>>> getAllHotels(@PageableDefault(size = 5, sort = { "name", "minPrice" },direction = Sort.Direction.ASC) Pageable pageable ) throws Exception {
    	
    	 Page<Hotel> page = hotelService.getAllHotels(pageable);
    	
    	List<Resource<Map<String, Object>>> resources  = page.getContent().stream()
    			.map(hotel -> {
    	            try {
    	                return hotelResourceBuilder.getHotelInstanceResource(hotel);
    	            } catch (Exception e) {
    	                throw new RuntimeException(e);
    	            }
    	        })
    	        .collect(Collectors.toList());
    	
        PagedModel<Resource<Map<String, Object>>> pagedModel = PagedModelBuilder.build(page, resources);

        return ResponseEntity.ok(pagedModel);
    }
    
    @PostMapping("/batch")
	public ResponseEntity<List<Resource<Map<String, Object>>>> insertListHotel(
			@RequestBody List<HotelDto> hotelDtoList) {
		List<Resource<Map<String, Object>>> resources = hotelService.saveAll(hotelDtoList).stream().map(hotel -> {
			try {
				return hotelResourceBuilder.getHotelInstanceResource(hotel);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList());
		return ResponseEntity.ok(resources);
	}
    
	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateHotel(@RequestBody HotelDto model, @PathVariable("id") UUID id) throws Exception {
		
		Hotel hotel = hotelService.updateHotel(id.toString(),model);
		Resource<Map<String, Object>> resource = hotelResourceBuilder.getHotelInstanceResource(hotel);
		return ResponseEntity.ok(resource);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteHotel(@PathVariable("id") UUID id) {
		
		Boolean status =  hotelService.softDeleteHotel(id.toString());
	    return ResponseEntity.ok(status); 
	}
	
	@PutMapping("/{id}/restore")
	public ResponseEntity<Boolean> restoreHotel(@PathVariable("id") UUID id) {
		Boolean status =  hotelService.restoreHotel(id.toString());
	    return ResponseEntity.ok(status);
	}
}
