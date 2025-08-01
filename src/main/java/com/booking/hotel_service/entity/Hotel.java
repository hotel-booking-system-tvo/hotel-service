package com.booking.hotel_service.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
	
	@Column(name = "\"name\"", columnDefinition = "NVARCHAR(255)")
	private String name;
	
	@Column(name = "\"address\"", columnDefinition = "NVARCHAR(255)")
	private String address;
	
	@Column(name = "\"description\"", columnDefinition = "NVARCHAR(255)")
	private String description;
	
	@CreationTimestamp
	@Column(name = "\"createdate\"")
	@CreatedDate
	private Date createdDate;
	
	@UpdateTimestamp
	@Column(name = "\"updateddate\"", nullable = false, updatable = false)
	@LastModifiedDate
	private Date updatedDate;
	
	@Column(name = "\"opentime\"")
	@JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
	private LocalDateTime openTime;
	
	@Column(name = "\"closetime\"")
	@JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
	private LocalDateTime closeTime;
	
	@Column(name = "\"minprice\"")
	private int minPrice;
	
	@Column(name = "\"maxprice\"")
	private int maxPrice;
	
	@Column(name = "\"createdby\"", columnDefinition = "NVARCHAR(255)")
	@CreatedBy
	private String createdBy;
	
	@Column(name = "\"updatedby\"", columnDefinition = "NVARCHAR(255)")
	private String updatedBy;
}
