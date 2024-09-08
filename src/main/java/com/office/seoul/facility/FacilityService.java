package com.office.seoul.facility;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class FacilityService {
	
	private final FacilityDao facilityDao;
	
	public FacilityService(FacilityDao facilityDao) {
		this.facilityDao = facilityDao;
	}

	public Object saveFacilities(List<FacilityDto> facilityDtos) {
		log.info("saveFacilities()");
		
		return facilityDao.insertFacilities(facilityDtos);
	}

	public List<FacilityDto> getAllFacilities() {
		
		return facilityDao.findAllFacilities();
	}
	
	public FacilityDto facilityDetailView(String id) {
		log.info("getFacilityById()");
		
        return facilityDao.findFacilityById(id);
	}


}
