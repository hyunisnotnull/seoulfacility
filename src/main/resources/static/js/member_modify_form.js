function MemberModifyForm() {
	console.log('MemberModifyForm()');
	
	let form = document.member_modify_form;
	if (form.u_m_id.value === '') {
		alert('아이디 입력하세요');
		form.u_m_id.focus();
		
	} else if (form.u_m_pw.value === '') {
		alert('비밀번호 입력하세요');
		form.u_m_pw.focus();
		
	} else if (form.u_m_pw.value !== form.u_m_pw.defaultValue) { 
		if (!confirm('비밀번호를 변경하시겠습니까?')) {
			return;
       }
			
	} else if (form.u_m_name.value === '') {
		alert('이름 입력하세요');
		form.u_m_name.focus();
				
	} else if (form.u_m_gender.value === '') {
		alert('성별을 선택하세요');
		form.u_m_gender.focus();
					
	} else if (form.u_m_mail.value === '') {
		alert('메일 주소 입력하세요');
		form.u_m_mail.focus();
				
	} else if (form.u_m_phone.value === '') {
		alert('전화번호 입력하세요');
		form.u_m_phone.focus();
					
	} else {
		form.submit();
		
	}
	
}