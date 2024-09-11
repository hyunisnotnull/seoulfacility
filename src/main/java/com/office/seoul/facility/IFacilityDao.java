package com.office.seoul.facility;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IFacilityDao {

	public List<FacilityDto> getAllFacility(String type);
	
	public List<FacilityDto> getFacilities(Map<String, Object> params);
	
	public int getTotalFacilitiesCount(String type);

	public FacilityDto getFacilityById(String id);

}
