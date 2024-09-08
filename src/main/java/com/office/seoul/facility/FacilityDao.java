package com.office.seoul.facility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class FacilityDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	public FacilityDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Object insertFacilities(List<FacilityDto> facilityDtos) {
		log.info("insertFacilities()");
		
		String sql = "INSERT INTO TBL_FACILITY(SVCID, SVCNM, DTLCONT, IMGURL, TELNO) VALUES(?, ?, ?, ?, ?)";
		
		int result = -1;
		try {
			
			for (FacilityDto dto : facilityDtos) {
                jdbcTemplate.update(sql, dto.getSvcid(), dto.getSvcnm(), dto.getDtlcont(), dto.getImgurl(), dto.getTelno());
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public List<FacilityDto> findAllFacilities() {
		log.info("findAllFacilities()");
		
		String sql = "SELECT SVCID, SVCNM, DTLCONT, IMGURL, TELNO FROM TBL_FACILITY";
		
		List<FacilityDto> facilityDtos = new ArrayList<>();

	    try {
	    	
	    	RowMapper<FacilityDto> rowMapper = BeanPropertyRowMapper.newInstance(FacilityDto.class);
	    	facilityDtos = jdbcTemplate.query(sql, rowMapper);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return facilityDtos.size() > 0 ? facilityDtos : null;
	}
	
	public FacilityDto findFacilityById(String id) {
		log.info("findFacilityById()");
		
		String sql = "SELECT SVCID, SVCNM, DTLCONT, IMGURL, TELNO FROM TBL_FACILITY WHERE SVCID = ?";

	    List<FacilityDto> facilityDtos = new ArrayList<>();

	    try {
	    	
	    	RowMapper<FacilityDto> rowMapper = BeanPropertyRowMapper.newInstance(FacilityDto.class);
	    	facilityDtos = jdbcTemplate.query(sql, rowMapper, id);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return facilityDtos.size() > 0 ? facilityDtos.get(0) : null;
	}

}
