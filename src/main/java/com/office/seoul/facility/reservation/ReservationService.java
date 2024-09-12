package com.office.seoul.facility.reservation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReservationService {

	private final IReservationDao iReservationDao;
	
	public ReservationService(IReservationDao iReservationDao) {
		this.iReservationDao = iReservationDao;
	}

	public List<String> getReservedTimes(String facilityId, String selectedDate) {
		
		List<ReservationDto> reservations = Arrays.asList();

        return reservations.stream()
                .map(ReservationDto::getR_use_time)
                .collect(Collectors.toList());
    }
		

	public List<String> getAvailableTime(String facilityId, String selectedDate) {

        List<String> allTimes = Arrays.asList("09:00", "11:00", "13:00", "15:00", "17:00", "19:00");
        

        List<String> reservedTimes = getReservedTimes(facilityId, selectedDate);
        

        return allTimes.stream()
                .filter(time -> !reservedTimes.contains(time))
                .collect(Collectors.toList());
    }

}
