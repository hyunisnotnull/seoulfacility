package com.office.seoul.facility.reservation;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    
    public Map<String, String> getReservationStatus(String id, LocalDate startDate, LocalDate endDate) {
        List<Map<String, Object>> reservationCounts = iReservationDao.findAllReservedDates(id, startDate, endDate);
        Map<String, String> reservationStatus = new HashMap<>();
        
        // 날짜별 예약 개수를 Map으로 변환
        Map<String, Integer> countMap = new HashMap<>();
        
        // 예약 개수가 있는 경우만 처리
        if (reservationCounts != null && !reservationCounts.isEmpty()) {
            for (Map<String, Object> row : reservationCounts) {
                String dateStr = (String) row.get("R_RESERVE_DATE");
                Integer count = ((Number) row.get("reservation_count")).intValue();
                countMap.put(dateStr, count);
            }
            log.info("First reservation date: {}", reservationCounts.get(0).get("R_RESERVE_DATE"));
        } else {
            log.info("No reservation counts found.");
        }
        
        // 예약 상태를 날짜별로 설정
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            String dateStr = date.toString();
            String isReserved = countMap.getOrDefault(dateStr, 0).toString();
            reservationStatus.put(dateStr, isReserved);
        }
        
        return reservationStatus;
    }

	public List<ReservationDto> getReservationsByMemberId(String u_m_id, int page, int size) {
		log.info("getReservationsByMemberId() with u_m_id: {}", u_m_id);
		int offset = (page - 1) * size;
		
		Map<String, Object> params = new HashMap<>();
	    params.put("u_m_id", u_m_id);
	    params.put("size", size);
	    params.put("offset", offset);
		
		return iReservationDao.findReservationsByMemberId(params);
	}

	public int cancelReservation(String reservationId) {
		log.info("cancelReservation() with reservationId: {}", reservationId);

		int result = iReservationDao.deleteReservationById(reservationId);
		
        if (result > 0) {
            log.info("Reservation cancelled successfully for ID: {}", reservationId);
        } else {
            log.warn("Failed to cancel reservation for ID: {}", reservationId);
        }

        return result;
    }

	public int countReservationsByMemberId(String u_m_id) {
		
		return iReservationDao.countReservationsByMemberId(u_m_id);
	}
}


