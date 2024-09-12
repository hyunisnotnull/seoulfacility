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
	
	/*
	 * 로그인
	 */
	@GetMapping("/member_login_result")
	public String memberLoginConfirm() {
		log.info("memberLoginConfirm()");
		
		String nextPage = "/member/member_login_result";
		
		return nextPage;
		
	}
	
	/*
	 * 정보수정
	 */
	@GetMapping("/member_modify_form")
	public String MemberModifyForm(Principal principal, Model model) {
		log.info("MemberModifyForm()");
		
		String nextPage = "/member/member_modify_form";
		
		MemberDto loginedMemberDto = memberService.MemberModifyForm(principal.getName());
		
		model.addAttribute("loginedMemberDto", loginedMemberDto);
		
		return nextPage;
	}

	/*
	 * 정보 수정 확인
	 */
	@PostMapping("/member_modify_confirm")
	public String memberModifyConfirm(MemberDto memberDto, Model model) {
		log.info("memberModifyConfirm()");
		
		String nextPage = "/member/member_modify_result";
		
		int result = memberService.memberModifyConfirm(memberDto);
		
		boolean isModify = result == MemberService.INSERT_SUCCESS_AT_DB;
	    model.addAttribute("isModify", isModify);
		
		return nextPage;
		
	}
	
	/*
	 * 회원 탈퇴
	 */
	@GetMapping("/member_delete_confirm")
	public String memberDeleteConfirm(Principal principal) {
		log.info("memberDeleteConfirm()");
		
		String nextPage = "redirect:/member/member_logout_confirm";

		int result = memberService.memberDeleteConfirm(principal.getName());
		if (result <= 0) 
			nextPage = "/member/member_delete_result";
		
		return nextPage;
		
	}
	
	/*
	 * 비밀번호 찾기
	 */
	@GetMapping("/find_password_form")
    public String findPasswordForm() {
        log.info("findPasswordForm()");
        
        return "member/find_password_form";
    }
	
	/*
	 * 비밀번호 찾기 확인
	 */
	@PostMapping("/find_password_confirm")
	public String findPasswordConfirm(MemberDto memberDto, Model model) {
		log.info("findPasswordConfirm()");
		
		String nextPage= "member/find_password_result";
		
		int result = memberService.findPasswordConfirm(memberDto);
		
		boolean isUpdate = result == MemberService.INSERT_SUCCESS_AT_DB;
	    model.addAttribute("isUpdate", isUpdate);
		
		return nextPage;
		
	}
	
	
}
