package com.office.seoul.facility.reservation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/reservation")
public class ReservationController {
	
	private final ReservationService reservationService;
	
	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	
	@GetMapping({"", "/"})
	public String reservation() {
		log.info("reservation()");
		
		String nextPage = "redirect:/reservation/home";
		
		return nextPage;
	}
	
	@GetMapping("/home")
	public String home() {
		log.info("home()");
		
		String nextPage = "reservation/home";
		
		return nextPage;
	}

}
