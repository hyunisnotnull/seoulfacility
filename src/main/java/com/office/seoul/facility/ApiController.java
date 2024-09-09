package com.office.seoul.facility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/api")
public class ApiController {
	
	@Value("${api-key}")
    private String apiKey;
	
	private static final Map<String, JSONArray> facilityDataStore = new ConcurrentHashMap<>();
	private FacilityService facilityService;
	
	public ApiController(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	
    @GetMapping("/data")
    public String facility(Model model) {
    	log.info("ApiController facility()");
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
        
        try {
            urlBuilder.append("/").append(URLEncoder.encode(apiKey, "UTF-8"));							// api 키
            urlBuilder.append("/").append(URLEncoder.encode("json", "UTF-8"));							// data type 결정
            urlBuilder.append("/").append(URLEncoder.encode("ListPublicReservationSport", "UTF-8"));	// 요청한 url
            urlBuilder.append("/").append(URLEncoder.encode("1", "UTF-8"));								// 페이지 시작 번호
            urlBuilder.append("/").append(URLEncoder.encode("446", "UTF-8"));							// 페이지 끝 번호 [최대 446]

            // HTTP 요청 및 응답 처리
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            
            int responseCode = conn.getResponseCode();
            BufferedReader rd;
            if (responseCode >= 200 && responseCode <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            
            // JSON 응답 파싱
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(url.openStream()));
            Map<String, Object> map = (Map<String, Object>) jsonObject.get("ListPublicReservationSport");
            JSONArray jsonArray = (JSONArray) map.get("row");
            
            // DB 저장 예시
            for(int i = 0; i < jsonArray.size(); i++) {
            List<FacilityDto> facilityDtos = ((List<JSONObject>) jsonArray).stream().map(json -> {
                        FacilityDto dto = new FacilityDto();
                        dto.setGubun((String) json.get("GUBUN"));
                        dto.setSvcid((String) json.get("SVCID"));
                        dto.setMaxclassnm((String) json.get("MAXCLASSNM"));
                        dto.setMinclassnm((String) json.get("MINCLASSNM"));
                        dto.setSvcstatnm((String) json.get("SVCSTATNM"));
                        dto.setSvcnm((String) json.get("SVCNM"));
                        dto.setPayatnm((String) json.get("PAYATNM"));
                        dto.setPlacenm((String) json.get("PLACENM"));
                        dto.setUsetgtinfo((String) json.get("USETGTINFO"));
                        dto.setSvcurl((String) json.get("SVCURL"));
                        dto.setX((String) json.get("X"));
                        dto.setY((String) json.get("Y"));
                        dto.setSvcopnbgndt((String) json.get("SVCOPNBGNDT"));
                        dto.setSvcopnenddt((String) json.get("SVCOPNENDDT"));
                        dto.setRcptbgndt((String) json.get("RCPTBGNDT"));
                        dto.setRcptenddt((String) json.get("RCPTENDDT"));
                        dto.setAreanm((String) json.get("AREANM"));
                        dto.setImgurl((String) json.get("IMGURL"));
                        dto.setImgurl((String) json.get("IMGURL"));
                        dto.setDtlcont((String) json.get("DTLCONT"));
                        dto.setTelno((String) json.get("TELNO"));
                        dto.setV_min((String) json.get("V_MIN"));
                        dto.setV_max((String) json.get("V_MAX"));
                        dto.setRevstddaynm((String) json.get("REVSTDDAYNM"));
                        dto.setRevstdday((String) json.get("REVSTDDAY"));
                        return dto;
                    }).collect(Collectors.toList());

            facilityService.saveFacilities(facilityDtos);
            	
            }

            facilityDataStore.put("facilityData", jsonArray);
            
            log.info("facilityData : ", jsonArray.toJSONString());
            
            return "redirect:/facility";
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; 
        }
    }
    
    public static JSONArray getStoredData() {
    	log.info("getStoredData()");
    	
        return facilityDataStore.get("facilityData");
    }

}
