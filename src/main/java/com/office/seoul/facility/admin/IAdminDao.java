package com.office.seoul.facility.admin;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAdminDao {

	public boolean isAdmin(String a_m_id);

	public int insertAdmin(AdminDto adminDto);

	public AdminDto selectAdminByAMId(String adminname);

	
	
}
