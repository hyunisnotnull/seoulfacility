package com.office.seoul.facility.reservation;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IReservationDao {

	Map<String, Object> insertReservation(String facilityId, String userId, String selectedDate);

}
