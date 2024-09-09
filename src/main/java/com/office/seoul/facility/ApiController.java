package com.office.seoul.facility;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/facility")
public class ApiController {

    private final FacilityService facilityService;

    public ApiController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping("/facilities")
    public String getFacilities(@RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "6") int size,
                                Model model) {
    	log.info("getFacilities()");
    	
    	String nextPage = "facility/home";
    	
        try {
            
        	List<FacilityDto> facilities = facilityService.getAllFacilities();
        	
            if (facilities == null || facilities.isEmpty()) {
                model.addAttribute("error", "No facilities found");
                log.warn("No facilities found");
                return "facilities";
            }
            
            // 페이지네이션 처리
            int start = Math.min(page * size, facilities.size());
            int end = Math.min(start + size, facilities.size());
            List<FacilityDto> pagedFacilities = facilities.subList(start, end);
            int totalFacilities = facilities.size();
            int totalPages = (int) Math.ceil((double) totalFacilities / size);

            model.addAttribute("facilities", pagedFacilities);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("pageSize", size);
            log.info("Total Facilities: {}", totalFacilities);
            log.info("Total Pages: {}", totalPages);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nextPage;
    }
    
    @GetMapping("/detailView/{id}")
    public String detailView(@PathVariable("id") String id, Model model) {
    	log.info("detailView()");
    	
    	String nextPage = "facility/detail_view";
    	
    	FacilityDto facilityDto = facilityService.detailView(id);
    	
    	model.addAttribute("facilityDto", facilityDto);
    	
    	return nextPage;
    }
}
