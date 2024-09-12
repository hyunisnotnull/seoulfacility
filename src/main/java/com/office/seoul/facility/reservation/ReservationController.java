package com.office.seoul.facility.reservation;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@PostMapping("/select")
    public String select(@RequestParam("facilityId") String facilityId,
                          @RequestParam("userId") String userId,
                          @RequestParam("selectedDate") String selectedDate,
                          Model model) {
        log.info("reserve() - facilityId: {}, userId: {}, selectedDate: {}", facilityId, userId, selectedDate);
        
        String nextPage = "reservation/select";
        
        List<String> availableTimes = reservationService.getAvailableTime(facilityId, selectedDate);
        
        model.addAttribute("facilityId", facilityId);
        model.addAttribute("userId", userId);
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("availableTimes", availableTimes);
        
        return nextPage;
    }
	
	

}
