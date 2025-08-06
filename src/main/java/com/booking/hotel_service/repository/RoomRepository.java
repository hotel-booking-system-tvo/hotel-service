package com.booking.hotel_service.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.booking.hotel_service.entity.Hotel;
import com.booking.hotel_service.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, String>,JpaSpecificationExecutor<Room> {

	Optional<Room> findById(String id);
	
	Optional<Room> findByIdAndDeletedFalse(String id);

	Page<Room> findAllByDeletedFalse(Pageable pageable);
}
