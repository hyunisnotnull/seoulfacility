package com.office.seoul.facility.reservation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.office.seoul.facility.util.TimeConfig;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReservationService {

	private final IReservationDao iReservationDao;
	
	public ReservationService(IReservationDao iReservationDao) {
		this.iReservationDao = iReservationDao;
	}

	public List<String> getReservedTimes(String facilityId, String selectedDate) {
        // 예약된 시간 조회
        return iReservationDao.findReservedTimes(facilityId, selectedDate);
    }

    public List<String> getAllTimes() {
        // 전체 시간 반환
        return TimeConfig.ALL_TIMES;
    }
    
    public boolean saveReservation(String facilityId, String userId, String selectedDate, String selectedTime) {
        try {
            iReservationDao.insertReservation(facilityId, userId, selectedDate, selectedTime);
            return true; // 저장 성공
        } catch (Exception e) {
            log.error("Error saving reservation", e);
            return false; // 저장 실패
        }
    }

}
