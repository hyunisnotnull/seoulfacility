package com.office.seoul.facility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilityDto {
	
	private int f_no;
	private String svcid;		//서비스id
	private String minclassnm;	//소분류명
	private String svcstatnm;	//서비스상태
	private String svcnm;		//서비스명
	private String payatnm;		//결제방법
	private String placenm;		//장소명
	private String usetgtinfo;	//서비스대상
	private String rcptbgndt;	//접수시작일시
	private String rcptenddt;	//접수종료일시
	private String areanm;		//지역명
	private String imgurl;		//이미지경로
	private String dtlcont;		//상세정보
	private String telno;		//전화번호
	private String v_min;		//서비스이용 시작시간
	private String v_max;		//서비스이용 종료시간
	private String revstddaynm;	//취소기간 기준정보
	private String revstdday;	//취소기간 기준일까지

}
