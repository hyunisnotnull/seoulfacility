package com.office.seoul.facility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class FacilityService {
	
	private final IFacilityDao iFacilityDao;
	
	public FacilityService(IFacilityDao iFacilityDao) {
		this.iFacilityDao = iFacilityDao;
	}

	public List<FacilityDto> home(String type) {
		
		return iFacilityDao.getAllFacility(type);
	}
	
	 public Map<String, Object> getFacilities(int page, int size, String type, String keyword) {
	        int offset = (page - 1) * size;
	        Map<String, Object> params = new HashMap<>();
	        params.put("offset", offset);
	        params.put("size", size);
	        params.put("type", type);
	        params.put("keyword", keyword);
	        
	        log.info("type : {}", type);
	        log.info("keyword : {}", keyword);

	        List<FacilityDto> facilities = iFacilityDao.getFacilities(params);
	        int totalItems = iFacilityDao.getTotalFacilitiesCount(params);

	        Map<String, Object> response = new HashMap<>();
	        response.put("items", facilities);
	        response.put("totalItems", totalItems);

	        return response;
	    }

	public FacilityDto getFacilityById(String id) {
		
		return iFacilityDao.getFacilityById(id);
	}

	public List<String> getAreas() {
		
        return iFacilityDao.getAreas();
    }

    public List<String> getCategories() {
    	
        return iFacilityDao.getCategories();
    }

    public List<String> getResults(String area, String category) {
        Map<String, Object> params = new HashMap<>();
        params.put("area", area);
        params.put("category", category);
        return iFacilityDao.getResults(params);
    }

}
