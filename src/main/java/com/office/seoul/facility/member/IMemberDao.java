package com.office.seoul.facility.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberDao {

	public boolean isMember(String u_m_id);

	public int insertMember(MemberDto memberDto);

}
