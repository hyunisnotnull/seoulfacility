package com.office.seoul.facility.reservation;

import com.office.seoul.facility.FacilityDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
	
	private int r_no;
	private FacilityDto facilityDto;
	private String u_m_id;
	private String r_reserve_date;
	private String r_reg_date;
	private String r_mod_date;
	private String r_use_state;
	private String r_use_time;

}
