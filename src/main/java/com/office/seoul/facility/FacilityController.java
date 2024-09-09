package com.office.seoul.facility;

import org.json.simple.JSONArray;
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
	public Object home(Model model) {
		log.info("home()");
		
		JSONArray facilities = ApiController.getStoredData();
        model.addAttribute("data", facilities);
        log.info("data: " + facilities.toJSONString());
		
		String nextPage = "facility/home";
		
		return nextPage;
	}
	
	@GetMapping("/detailView")
	public String detailView(@RequestParam("id") String id, Model model) {
		log.info("detailView");
	    
		String nextPage = "facility/detail_view";
        
        FacilityDto facilityDto = facilityService.facilityDetailView(id);
        
        model.addAttribute("facility", facilityDto);
        
        return nextPage;
	}

}
