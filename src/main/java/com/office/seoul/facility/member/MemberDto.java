package com.office.seoul.facility.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
	
	private int u_m_no;
	private String u_m_id;
	private String u_m_pw;
	private String u_m_name;
	private String u_m_gender;
	private String u_m_mail;
	private String u_m_phone;
	private String u_m_reg_date;
	private String u_m_mod_date;
	private String u_m_role;
	
}
