package com.office.seoul;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.office.seoul.facility.FacilityService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {
	
	private final FacilityService facilityService;
	
	public HomeController(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
    
    @GetMapping({"/", ""})
    public String home(Model model) {
    	
        log.info("home()");
        
        String nextPage = "home";
        
        return nextPage;
    }
    
    @GetMapping("/findfour")
    @ResponseBody
    public Map<String, Object> findFour() {
      log.info("findFour()");
      
      Map<String, Object> options = new HashMap<>();
      options.put("results", facilityService.getFindFour());
      
      return options;
   }
    
}
