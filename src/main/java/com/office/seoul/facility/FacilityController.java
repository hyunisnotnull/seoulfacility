package com.office.seoul.facility;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String home(@RequestParam(value = "type", required = false) String type, Model model) {
		log.info("home()");
		
		String nextPage = "facility/home";
		
		List<FacilityDto> facilityDto = facilityService.home(type);
		
		model.addAttribute("facilityDto", facilityDto);
		model.addAttribute("selectedType", type);
		
		return nextPage;
	}
	
	@GetMapping("/page")
	@ResponseBody
    public Map<String, Object> getFacilities(	@RequestParam(value = "page", defaultValue = "1") int page, 
            									@RequestParam(value = "size", defaultValue = "6") int size,
            									@RequestParam(value = "type", required = false) String type,
            									@RequestParam(value = "keyword", required = false) String keyword) {
		
		log.info("Request Params - page: {}, size: {}, type: {}, keyword: {}", page, size, type, keyword);
		
	    Map<String, Object> response = facilityService.getFacilities(page, size, type, keyword);
	    
	    return response;
	    
    }
	
	@GetMapping("/detailView/{id}")
	public String detailView(@PathVariable("id") String id, Model model) {
		log.info("detailView() with id: {}", id);
		
		String nextPage = "facility/detail_view";
	    
	    FacilityDto facilityDto = facilityService.getFacilityById(id);
	    if (facilityDto != null) {
	        model.addAttribute("facilityDto", facilityDto);
	    } else {
	        model.addAttribute("error", "Facility not found");
	    }
	    
	    return nextPage; 
	}

}
