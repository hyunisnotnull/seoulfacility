package com.office.seoul.facility.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.office.seoul.facility.member.MemberDto;
import com.office.seoul.facility.member.MemberService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {

	final private AdminService adminService;
	
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	
	@GetMapping({"", "/"})
	public String admin() {
		log.info("admin()");
		
		String nextPage = "redirect:/admin/home";
		
		return nextPage;
	}
	
	@GetMapping("/home")
	public String home() {
		log.info("admin home()");
		
		String nextPage = "admin/home";
		
		return nextPage;
	}
	
	
	/*
	 * 어드민 가입
	 */
	
	@GetMapping("/create_account_form")
	public String createAccountForm() {
		log.info("createAccountForm()");
		
		String nextPage = "admin/create_account_form";
		
		return nextPage;
	}
	
	
	/*
	 *  어드민 가입 확인
	 */
	@PostMapping("/create_account_confirm")
	public String createAccountConfirm(AdminDto adminDto, Model model) {
		log.info("createAccountConfirm()");
		
		String nextPage = "admin/create_account_result";
		
		int result = adminService.createAccountConfirm(adminDto);
	    
	    boolean isSuccess = result == adminService.INSERT_SUCCESS_AT_DB;
	    model.addAttribute("isSuccess", isSuccess);
	    
	    return nextPage;
	}

	
	/*
	 * 어드민 로그인
	 */
	@GetMapping("/admin_login_form")
	public String admin_login_form() {
		log.info("adminLoginForm()");
		
		String nextPage = "/admin/admin_login_form";
		
		return nextPage;
		
	}
	

	@GetMapping("/admin_login_result")
	public String admin_login_result() {
		log.info("adminLoginConfirm()");
		
		String nextPage = "/admin/admin_login_result";
		
		return nextPage;
		
	}
	
}
