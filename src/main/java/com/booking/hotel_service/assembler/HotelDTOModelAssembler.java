package com.booking.hotel_service.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.booking.hotel_service.dto.HotelDto;

public class HotelDTOModelAssembler implements RepresentationModelAssembler<HotelDto, EntityModel<HotelDto>> {

	@Override
	public EntityModel<HotelDto> toModel(HotelDto entity) {
		
		return EntityModel.of(entity);
	}

}
