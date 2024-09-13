package com.office.seoul.facility.admin;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.office.seoul.facility.member.MemberDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminService {

	final static public int ID_EXISTS 					= -2;
	final static public int DB_COMMUNICATION_TROUBLE 	= -1;
	final static public int INSERT_FAIL_AT_DB 			= 0;
	final static public int INSERT_SUCCESS_AT_DB 		= 1;
	
	final private IAdminDao iAdminDao;
	final private PasswordEncoder passwordEncoder;
	final private JavaMailSenderImpl javaMailSenderImpl;
	
	public AdminService(IAdminDao iAdminDao, PasswordEncoder passwordEncoder, JavaMailSenderImpl javaMailSenderImpl ) {
	  this.iAdminDao = iAdminDao;
	  this.passwordEncoder = passwordEncoder;
	  this.javaMailSenderImpl = javaMailSenderImpl;
	  
	}
	
	
	public int createAccountConfirm(AdminDto adminDto) {
		log.info("admin createAccountConfirm()");
		
		boolean isAdmin = iAdminDao.isAdmin(adminDto.getA_m_id());
		if (!isAdmin) {
			adminDto.setA_m_pw(passwordEncoder.encode(adminDto.getA_m_pw()));
			int result = iAdminDao.insertAdmin(adminDto);
			
			switch (result) {
			case DB_COMMUNICATION_TROUBLE:
				log.info("DATABASE COMMUNICATION TROUBLE");
				break;

			case INSERT_FAIL_AT_DB:
				log.info("INSERT FAIL AT DATABASE");
				break;
				
			case INSERT_SUCCESS_AT_DB:
				log.info("INSERT SUCCESS AT DATABASE");
				break;
			}
			
			return result;
			
		} else {
			return ID_EXISTS;
			
		}
	}
	
	
	
	
	
	
	
}
