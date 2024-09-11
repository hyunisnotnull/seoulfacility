package com.office.seoul.facility;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/facility")
public class FacilityController {
	
	private final FacilityService facilityService;
	
	public FacilityController(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	
	@GetMapping({"", "/"})
	public String facility() {
		log.info("facility()");
		
		String nextPage = "redirect:/facility/home";
		
		return nextPage;
	}
	
	@GetMapping("/home")
	public String home(Model model) {
		log.info("home()");
		
		String nextPage = "facility/home";
		
		List<FacilityDto> facilityDto = facilityService.home();
		
		model.addAttribute("facilityDto", facilityDto);
		
		return nextPage;
	}
	
	@GetMapping("/pagedata")
    public Map<String, Object> getFacilities(	@RequestParam(value = "page", defaultValue = "1") int page, 
            									@RequestParam(value = "size", defaultValue = "6") int size) {
        return facilityService.getFacilities(page, size);
    }

}
