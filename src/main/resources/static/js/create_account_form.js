function createAccountForm() {
    console.log('createAccountForm()');
    
    let form = document.create_account_form;
    
	// 정규식 정의
    const emailRegex = /^[^\s@]+@[^\s@]+\.(com|net|org|co\.kr)$/; // 이메일 도메인 제한
    const pwRegex = /^.{6,}$/;                                   // 비밀번호 6자리 이상
    const idRegex = /^[a-zA-Z0-9]{6,15}$/;                       // 아이디: 영어 대소문자, 숫자 포함, 6~15자리
    const nameRegex = /^[가-힣a-zA-Z]{3,20}$/;                    // 이름: 한글, 영어 대소문자, 3~20자리
    const phoneRegex = /^\d{3}-\d{4}-\d{4}$/;                    // 전화번호: 000-0000-0000 형식

    if (form.u_m_id.value === '') {
        alert('새로운 아이디 입력하세요');
        form.u_m_id.focus();
        return;
    } else if (!idRegex.test(form.u_m_id.value)) {
        alert('아이디는 6자 이상 15자 이하의 영어 또는 숫자여야 합니다.');
        form.u_m_id.focus();
        return;
    }

    if (form.u_m_pw.value === '') {
        alert('새로운 비밀번호 입력하세요');
        form.u_m_pw.focus();
        return;
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
        alert('이름은 3자 이상 20자 이하의 한글 또는 영어여야 합니다.');
        form.u_m_name.focus();
        return;
    }

    if (form.u_m_gender.value === '') {
        alert('성별을 선택하세요');
        form.u_m_gender.focus();
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

    form.submit();
}