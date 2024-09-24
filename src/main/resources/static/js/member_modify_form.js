function MemberModifyForm() {
	console.log('MemberModifyForm()');
	
	let form = document.member_modify_form;

    // 정규식 정의
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;    // 올바른 이메일 형식
    const pwRegex = /^.{6,}$/;                          // 비밀번호 6자리 이상
    const nameRegex = /^[가-힣a-zA-Z]{3,8}$/;      		// 이름: 한글, 영어 대소문자, 3~8자리
    const phoneRegex = /^\d{3}-\d{4}-\d{4}$/;           // 전화번호: 000-0000-0000 형식

	// 원래 값
    const originalPw = document.querySelector('input[name="u_m_pw"]').defaultValue;
    const originalName = document.querySelector('input[name="u_m_name"]').defaultValue;
    const originalMail = document.querySelector('input[name="u_m_mail"]').defaultValue;
    const originalPhone = document.querySelector('input[name="u_m_phone"]').defaultValue;

    
	if (form.u_m_pw.value === '') {
	    // 기존 비밀번호 유지
	    form.u_m_pw.value = originalPw; 
	} else if (!pwRegex.test(form.u_m_pw.value)) {
	    alert('비밀번호는 6자 이상이어야 합니다.');
	    form.u_m_pw.focus();
	    return;
	}

    if (form.u_m_name.value === '') {
        alert('새로운 이름 입력하세요');
        form.u_m_name.focus();
        return;
    } else if (!nameRegex.test(form.u_m_name.value)) {
        alert('이름은 3자 이상 8자 이하의 한글, 영어 또는 숫자여야 합니다.(띄어쓰기 불가능)');
        form.u_m_name.focus();
        return;
    }

    if (form.u_m_mail.value === '') {
        alert('새로운 메일 주소 입력하세요');
        form.u_m_mail.focus();
        return;
    } else if (!emailRegex.test(form.u_m_mail.value)) {
        alert('올바른 이메일 주소를 입력하세요.');
        form.u_m_mail.focus();
        return;
    }

    if (form.u_m_phone.value === '') {
        alert('새로운 전화번호 입력하세요');
        form.u_m_phone.focus();
        return;
    } else if (!phoneRegex.test(form.u_m_phone.value)) {
        alert('전화번호는 "000-0000-0000" 형식이어야 합니다.');
        form.u_m_phone.focus();
        return;
    }
	
	// 변경 여부 확인
    if (form.u_m_pw.value === originalPw && 
        form.u_m_name.value === originalName &&
        form.u_m_mail.value === originalMail &&
        form.u_m_phone.value === originalPhone) {
        alert('변경된 정보가 없습니다.');
        return;
    }

    form.submit();
}