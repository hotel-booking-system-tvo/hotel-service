package com.booking.hotel_service.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="\"rooms\"")
@EntityListeners(AuditingEntityListener.class)
public class Room  {

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
    
	@Column(name = "\"description\"", columnDefinition = "NVARCHAR(255)")
    private String description;
	
    private String roomType; // ENUM: SINGLE, DOUBLE, SUITE, etc.
    private Integer capacity;
    private Integer bedcount;
    private BigDecimal price;
    

    @Column(name = "available")
    private Boolean isAvailable = true;

    private String status; // AVAILABLE, RESERVED, OCCUPIED, MAINTENANCE
	
    
	@Column(name = "deleted", nullable = false)
	private Boolean deleted = false;
	
	@CreationTimestamp
	@Column(name = "\"createdate\"", nullable = false, updatable = false)
	@CreatedDate
	private Date createdDate;
	
	@Column(name = "\"updateddate\"")
	@LastModifiedDate
	private Date updatedDate;
	
	@Column(name = "\"createdby\"", columnDefinition = "NVARCHAR(255)")
	@CreatedBy
	private String createdBy;
	
	@Column(name = "\"updatedby\"", columnDefinition = "NVARCHAR(255)")
	private String updatedBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hotel_id", nullable = false)
	private Hotel hotel;
}
