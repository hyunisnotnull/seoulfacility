package com.office.seoul.facility.member;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	 * 회원가입
	 */
	@GetMapping("/create_account_form")
	public String createAccountForm() {
		log.info("createAccountForm()");
		
		String nextPage = "member/create_account_form";
		
		return nextPage;
	}
	
	/*
	 * 회원가입 확인
	 */
	@PostMapping("/create_account_confirm")
	public String createAccountConfirm(MemberDto memberDto, Model model) {
		log.info("createAccountConfirm()");
		
		String nextPage = "member/create_account_result";
		
		int result = memberService.createAccountConfirm(memberDto);
	    
	    boolean isSuccess = result == MemberService.INSERT_SUCCESS_AT_DB;
	    model.addAttribute("isSuccess", isSuccess);
	    
	    return nextPage;
	}

	/*
	 * 로그인
	 */
	@GetMapping("/member_login_form")
	public String memberLoginForm() {
		log.info("memberLoginForm()");
		
		String nextPage = "/member/member_login_form";
		
		return nextPage;
		
	}
	
	@GetMapping("/member_modify_form")
	public String MemberModifyForm(Principal principal, Model model) {
		log.info("MemberModifyForm()");
		
		String nextPage = "/member/member_modify_form";
		
		MemberDto loginedMemberDto = memberService.MemberModifyForm(principal.getName());
		
		model.addAttribute("loginedMemberDto", loginedMemberDto);
		
		return nextPage;
	}

	
}
