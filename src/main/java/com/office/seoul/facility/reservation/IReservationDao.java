package com.office.seoul.facility.reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IReservationDao {

	List<String> findReservedTimes(@Param("facilityId") String facilityId, @Param("selectedDate") String selectedDate);

	void insertReservation(@Param("facilityId") String facilityId, @Param("userId") String userId, @Param("selectedDate") String selectedDate, @Param("selectedTime") String selectedTime);

	List<Map<String, Object>> findAllReservedDates(@Param("id") String id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
