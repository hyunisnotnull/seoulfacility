package com.office.seoul.facility;

import java.util.List;

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
	
//	@GetMapping
//    public String getFacilityPage(Model model) {
//        // 데이터 로드
//        List<FacilityDto> facilities = facilityService.getAllFacilities();
//        model.addAttribute("data", facilities);
//
//        // 뷰 페이지 반환
//        return "facility/facility"; 
//    }
	
	@GetMapping("/detailView")
	public String detailView(@RequestParam("id") String id, Model model) {
		log.info("detailView");
	    
		String nextPage = "facility/detail_view";
        
        FacilityDto facilityDto = facilityService.facilityDetailView(id);
        
        model.addAttribute("facility", facilityDto);
        
        return nextPage;
	}

}
