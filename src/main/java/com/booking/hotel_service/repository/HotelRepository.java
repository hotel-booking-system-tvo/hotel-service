package com.booking.hotel_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.booking.hotel_service.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String>,JpaSpecificationExecutor<Hotel> {

	Optional<Hotel> findById(String id);
}
