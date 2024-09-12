package com.office.seoul.facility.member;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
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
	final private JavaMailSenderImpl javaMailSenderImpl;
	
	
	public MemberService(IMemberDao iMemberDao, PasswordEncoder passwordEncoder, JavaMailSenderImpl javaMailSenderImpl) {
		this.iMemberDao = iMemberDao;
		this.passwordEncoder = passwordEncoder;
		this.javaMailSenderImpl = javaMailSenderImpl;
	
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
		
		if (memberDto.getU_m_pw() != null && !memberDto.getU_m_pw().isEmpty()) {
			String encodedPassword = passwordEncoder.encode(memberDto.getU_m_pw());
	        memberDto.setU_m_pw(encodedPassword);
		}
		
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

	public int findPasswordConfirm(MemberDto memberDto) {
		log.info("findPasswordConfirm()");
	    
	    MemberDto dto = iMemberDao.selectForFindPassword(
	            memberDto.getU_m_id(),
	            memberDto.getU_m_name(),
	            memberDto.getU_m_mail());
		
		int result = 0;
		if (dto != null) {
			
			final String newPassword = createNewPassword();
			// 비밀번호 암호화
			String encodedPassword = passwordEncoder.encode(newPassword);
            result = iMemberDao.updatePassword(memberDto.getU_m_id(), encodedPassword);
			if (result > 0) 
				sendNewPasswordByMail(memberDto.getU_m_mail(), newPassword);
			
		}
		log.info("result : {}", result);
		return result;

	}

	private String createNewPassword() {
		log.info("createNewPassword()");
		
		char[] chars = new char[] {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
				'u', 'v', 'w', 'x', 'y', 'z'
				};
		
		StringBuffer stringBuffer = new StringBuffer();
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(new Date().getTime());
		
		int index = 0;
		int length = chars.length;
		for (int i = 0; i < 8; i++) {
			index = secureRandom.nextInt(length);
			
			if (index % 2 == 0) {
				stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
			} else {
				stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
			}
		}
		
		return stringBuffer.toString();
		
	}

	private void sendNewPasswordByMail(String toMailAddr, String newPassword) {
		log.info("sendNewPasswordByMail()");
		
		final MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMessageHelper.setTo("wkdwlsdjs@gmail.com");
				mimeMessageHelper.setSubject("[공공예약서비스] 새 비밀번호 안내입니다.");
				mimeMessageHelper.setText("새 비밀번호 : " + newPassword, true);
				
			}
			
		};
		
		javaMailSenderImpl.send(mimeMessagePreparator);
		
	}
	
}
