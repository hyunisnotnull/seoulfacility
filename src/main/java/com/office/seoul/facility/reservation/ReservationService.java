package com.office.seoul.facility.reservation;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReservationService {

	private final IReservationDao iReservationDao;
	
	public ReservationService(IReservationDao iReservationDao) {
		this.iReservationDao = iReservationDao;
	}
}
