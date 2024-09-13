/*
 * package com.office.seoul.facility.admin;
 * 
 * import org.springframework.security.core.userdetails.User; import
 * org.springframework.security.core.userdetails.UserDetails; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.security.core.userdetails.UsernameNotFoundException;
 * import org.springframework.stereotype.Service;
 * 
 * import lombok.extern.log4j.Log4j2;
 * 
 * @Log4j2
 * 
 * @Service public class AdminDetailService implements UserDetailsService {
 * 
 * final private IAdminDao iAdminDao;
 * 
 * public AdminDetailService(IAdminDao iAdminDao) { this.iAdminDao = iAdminDao;
 * 
 * }
 * 
 * @Override public UserDetails loadUserByUsername(String adminname) throws
 * UsernameNotFoundException {
 * 
 * AdminDto selectedAdminDto = iAdminDao.selectAdminByAMId(adminname);
 * 
 * if (selectedAdminDto != null) { return User.builder()
 * .username(selectedAdminDto.getA_m_id())
 * .password(selectedAdminDto.getA_m_pw())
 * .roles(selectedAdminDto.getA_m_role()) .build();
 * 
 * }
 * 
 * return null;
 * 
 * }
 * 
 * 
 * }
 */