package com.office.seoul.facility.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MemberService {
	
	final static public int ID_EXISTS 					= -2;
	final static public int DB_COMMUNICATION_TROUBLE 	= -1;
	final static public int INSERT_FAIL_AT_DB 			= 0;
	final static public int INSERT_SUCCESS_AT_DB 		= 1;
	
	final private IMemberDao iMemberDao;
	final private PasswordEncoder passwordEncoder;
	
	
	public MemberService(IMemberDao iMemberDao, PasswordEncoder passwordEncoder) {
		this.iMemberDao = iMemberDao;
		this.passwordEncoder = passwordEncoder;
	
	}

	public int createAccountConfirm(MemberDto memberDto) {
		log.info("createAccountConfirm()");
		
		boolean isMember = iMemberDao.isMember(memberDto.getU_m_id());
		if (!isMember) {
			memberDto.setU_m_pw(passwordEncoder.encode(memberDto.getU_m_pw()));
			int result = iMemberDao.insertMember(memberDto);
			
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

	public MemberDto MemberModifyForm(String loginedMemberID) {
		log.info("MemberModifyForm()");
			
		return iMemberDao.getLoginedMemberByUmId(loginedMemberID);
	}

	public int memberModifyConfirm(MemberDto memberDto) {
		log.info("memberModifyConfirm()");
		
		int result = iMemberDao.updateMemberModify(memberDto);
		
		switch (result) {
		case INSERT_FAIL_AT_DB:
			log.info("회원 정보수정 실패");
			break;
			
		case INSERT_SUCCESS_AT_DB:
			log.info("회원 정보수정 성공");
			break;
		}
		
		return result;
	}

	public int memberDeleteConfirm(String loginedMemberID) {
		log.info("memberDeleteConfirm()");
			
		return iMemberDao.deleteMemberByMId(loginedMemberID);
	}

	
}
