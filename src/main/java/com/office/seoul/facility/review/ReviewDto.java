package com.office.seoul.facility.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
	
	private int c_no;
	private String SVCID;
	private String u_m_id;
	private String c_text;
	private int c_rank;
	private String c_reg_date;
	private String c_mod_date;

}
