package com.booking.hotel_service.entity;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name ="\"hotel\"")
public class Hotel {

	@SuppressWarnings("deprecation")
	@Id
	@Column(name = "\"id\"")
	@GeneratedValue(
			generator = "UUID"
			)
	@GenericGenerator(
	name = "UUID",
	strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	
	@Column(name = "\"name\"")
	private String name;
	
	@Column(name = "\"address\"")
	private String address;
	
	@Column(name = "\"description\"")
	private String description;
	
	@Column(name = "\"createDate\"")
	@LastModifiedDate
	@JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
	private LocalDate createdDate;
	
	@Column(name = "\"updatedDate\"")
	@LastModifiedDate
	@JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
	private LocalDate updatedDate;
	
	@Column(name = "\"openTime\"")
	@LastModifiedDate
	@JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
	private LocalDate openTime;
	
	@Column(name = "\"closeTime\"")
	@LastModifiedDate
	@JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
	private LocalDate closeTime;
	
	@Column(name = "\"minPrice\"")
	private int minPrice;
	
	@Column(name = "\"maxPrice\"")
	private int maxPrice;
	
	@Column(name = "\"createdBy\"")
	private String createdBy;
	
	@Column(name = "\"updatedBy\"")
	private String updatedBy;
}
