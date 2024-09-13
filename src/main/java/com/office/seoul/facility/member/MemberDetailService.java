package com.office.seoul.facility.member;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MemberDetailService implements UserDetailsService {
	
	final private IMemberDao iMemberDao;
	
	public MemberDetailService(IMemberDao iMemberDao) {
		this.iMemberDao = iMemberDao;
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		MemberDto selectedMemberDto = 
				iMemberDao.selectMemberByMId(username);
		
		if (selectedMemberDto != null) {
			return User.builder()
					.username(selectedMemberDto.getU_m_id())
					.password(selectedMemberDto.getU_m_pw())
					.roles(selectedMemberDto.getU_m_role())
					.build();
		}
		
		return null;
		
	}
	

}
