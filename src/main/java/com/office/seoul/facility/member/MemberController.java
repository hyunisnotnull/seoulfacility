package com.office.seoul.facility.member;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.office.seoul.facility.FacilityDto;
import com.office.seoul.facility.FacilityService;
import com.office.seoul.facility.reservation.ReservationDto;
import com.office.seoul.facility.reservation.ReservationService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {

	final private MemberService memberService;
	final private ReservationService reservationService;
	final private FacilityService facilityService;

	public MemberController(MemberService memberService, ReservationService reservationService,
			FacilityService facilityService) {
		this.memberService = memberService;
		this.reservationService = reservationService;
		this.facilityService = facilityService;
	}

	// 회원가입
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
	 * 로그인 성공
	 * 
	 * @GetMapping("/member_login_confirm") public String memberLoginConfirm() {
	 * log.info("memberLoginConfirm()");
	 * 
	 * String nextPage = "redirect:/";
	 * 
	 * return nextPage;
	 * 
	 * }
	 */

	
	 //로그인 실패
	 @GetMapping("/member_login_result") public String memberLoginFail() {
	 log.info("memberLoginFail()");
	  
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

		// 로그인한 회원 정보 조회
		MemberDto loginedMemberDto = memberService.MemberModifyForm(principal.getName());
		log.info("MemberModifyForm() with loginedMemberDto: {}", loginedMemberDto);

		// 모델에 회원 정보 추가
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

	// 예약 확인 보기
	@GetMapping("/member_reservation_form")
	public String MemberReservationForm(Principal principal, Model model,
			@RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "size", defaultValue = "5") int size) {
		
		log.info("MemberReservationForm()");
		log.info("MemberReservationForm() with page: {}, size: {}", page, size);
		
		String nextPage = "/member/member_reservation_form";

		// 로그인한 회원 정보 조회
		MemberDto loginedMemberDto = memberService.MemberModifyForm(principal.getName());
		log.info("MemberModifyForm() with loginedMemberDto: {}", loginedMemberDto);

		// 해당 회원의 예약 정보 조회
		List<ReservationDto> reservations = reservationService.getReservationsByMemberId(loginedMemberDto.getU_m_id(), page, size);
		log.info("MemberModifyForm() with reservations: {}", reservations);

		// 예약된 시설 ID 목록 추출 (중복 허용)
		List<String> facilityIds = reservations.stream().map(ReservationDto::getSVCID).collect(Collectors.toList());

		// 시설 정보 조회
		List<FacilityDto> facilityMap = facilityService.getFacilitiesByIds(facilityIds);
		
		// 전체 예약 수 조회
	    int totalReservations = reservationService.countReservationsByMemberId(loginedMemberDto.getU_m_id());
	    log.info("Total reservations: {}", totalReservations);

		// 모델에 회원 정보 및 예약 정보 추가
		model.addAttribute("reservations", reservations);
		model.addAttribute("facilityMap", facilityMap);
		model.addAttribute("totalReservations", totalReservations);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", (int) Math.ceil((double) totalReservations / size));

		return nextPage;
	}

	// 예약 취소 확인

	@PostMapping("/member_reservation_confirm")
	public ResponseEntity<String> memberReservationConfirm(@RequestParam("r_no") String reservationId) {
		log.info("memberReservationConfirm() with reservationId: {}", reservationId);

		// 예약 취소 처리 로직 추가
		int result = reservationService.cancelReservation(reservationId);

		if (result > 0) {
			return ResponseEntity.ok("예약이 취소되었습니다.");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약 취소에 실패했습니다.");
		}
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
	 * 아이디 찾기
	 */
	@GetMapping("/find_id_form")
	public String findIdForm() {
		log.info("findIdForm()");

		return "member/find_id_form";
	}

	/*
	 * 아이디 찾기 확인
	 */
	@PostMapping("/find_id_confirm")
	public String findIdConfirm(MemberDto memberDto, Model model) {
		log.info("findIdConfirm()");

		String nextPage = "member/find_id_result";

		MemberDto dto = memberService.findIdConfirm(memberDto);

		if (dto != null) {
			model.addAttribute("isUpdate", true);
			model.addAttribute("name", memberDto.getU_m_name());
			model.addAttribute("userId", dto.getU_m_id());

		} else {
			model.addAttribute("isUpdate", false);

		}

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

		String nextPage = "member/find_password_result";

		int result = memberService.findPasswordConfirm(memberDto);

		boolean isUpdate = result == MemberService.INSERT_SUCCESS_AT_DB;
		model.addAttribute("isUpdate", isUpdate);

		return nextPage;

	}

	// 권한 거부
	@GetMapping("/access_denied")
	public String accessDenied() {
		log.info("accessDenied()");

		String nextPage = "member/access_denied";

		return nextPage;
	}
}
