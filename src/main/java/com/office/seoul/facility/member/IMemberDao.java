package com.office.seoul.facility.member;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IMemberDao {

	public boolean isMember(String u_m_id);

	public int insertMember(MemberDto memberDto);

	public MemberDto selectMemberByMId(String username);

	public MemberDto getLoginedMemberByUmId(String loginedMemberID);

	public int updateMemberModify(MemberDto memberDto);

	public int deleteMemberByMId(String loginedMemberID);

	public MemberDto selectForFindPassword(@Param("u_m_id") String u_m_id,
								            @Param("u_m_name") String u_m_name,
								            @Param("u_m_mail") String u_m_mail);

	public int updatePassword(@Param("u_m_id") String u_m_id, @Param("encodedPassword") String encodedPassword);

	public MemberDto selectForFindId(@Param("u_m_name") String u_m_name, @Param("u_m_mail") String u_m_mail);

}
