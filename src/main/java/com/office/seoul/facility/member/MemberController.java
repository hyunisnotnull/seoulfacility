package com.office.seoul.facility.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {
	
	final private MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	/*
	 * 회원가입 양식
	 */
	@GetMapping("/create_account_form")
	public String createAccountForm() {
		log.info("createAccountForm()");
		
		String nextPage = "member/create_account_form";
		
		return nextPage;
	}

}
