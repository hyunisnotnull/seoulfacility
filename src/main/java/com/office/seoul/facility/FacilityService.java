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
	
	 public Map<String, Object> getFacilities(int page, int size, String type) {
	        int offset = (page - 1) * size;
	        Map<String, Object> params = new HashMap<>();
	        params.put("offset", offset);
	        params.put("size", size);
	        params.put("type", type);
	        
	        log.info("type : {}", type);

	        List<FacilityDto> facilities = iFacilityDao.getFacilities(params);
	        int totalItems = iFacilityDao.getTotalFacilitiesCount(type);

	        Map<String, Object> response = new HashMap<>();
	        response.put("items", facilities);
	        response.put("totalItems", totalItems);

	        return response;
	    }

	public FacilityDto getFacilityById(String id) {
		
		return iFacilityDao.getFacilityById(id);
	}

}
