package com.booking.hotel_service.projection;

import java.math.BigDecimal;

public interface RoomView {
	String getId();
	String getName();
    String getDescription();
    String getRoomType();
    Integer getCapacity();
    Integer getBedcount();
    BigDecimal getPrice();
    Boolean getIsAvailable();
    String getStatus();

    HotelInfo getHotel();

    interface HotelInfo {
        String getId();
        String getName();
        String getAddress();
    }
}
