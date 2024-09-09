package com.office.seoul.facility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class FacilityService {

	@Value("${api-key}")
    private String apiKey;

	private final List<FacilityDto> facilities = new ArrayList<>();

    public List<FacilityDto> getAllFacilities() {

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
            
            facilities.clear();
            for (Object obj : jsonArray) {
                JSONObject jsonObjectForDto = (JSONObject) obj;
                FacilityDto facilityDto = new FacilityDto();
                facilityDto.setGubun((String) jsonObjectForDto.get("GUBUN"));
                facilityDto.setSvcid((String) jsonObjectForDto.get("SVCID"));
                facilityDto.setMaxclassnm((String) jsonObjectForDto.get("MAXCLASSNM"));
                facilityDto.setMinclassnm((String) jsonObjectForDto.get("MINCLASSNM"));
                facilityDto.setSvcstatnm((String) jsonObjectForDto.get("SVCSTATNM"));
                facilityDto.setSvcnm((String) jsonObjectForDto.get("SVCNM"));
                facilityDto.setPayatnm((String) jsonObjectForDto.get("PAYATNM"));
                facilityDto.setPlacenm((String) jsonObjectForDto.get("PLACENM"));
                facilityDto.setUsetgtinfo((String) jsonObjectForDto.get("USETGTINFO"));
                facilityDto.setSvcurl((String) jsonObjectForDto.get("SVCURL"));
                facilityDto.setX((String) jsonObjectForDto.get("X"));
                facilityDto.setY((String) jsonObjectForDto.get("Y"));
                facilityDto.setSvcopnbgndt((String) jsonObjectForDto.get("SVCOPNBGNDT"));
                facilityDto.setSvcopnenddt((String) jsonObjectForDto.get("SVCOPNENDDT"));
                facilityDto.setRcptbgndt((String) jsonObjectForDto.get("RCPTBGNDT"));
                facilityDto.setRcptenddt((String) jsonObjectForDto.get("RCPTENDDT"));
                facilityDto.setAreanm((String) jsonObjectForDto.get("AREANM"));
                facilityDto.setImgurl((String) jsonObjectForDto.get("IMGURL"));
                facilityDto.setDtlcont((String) jsonObjectForDto.get("DTLCONT"));
                facilityDto.setTelno((String) jsonObjectForDto.get("TELNO"));
                facilityDto.setV_min((String) jsonObjectForDto.get("V_MIN"));
                facilityDto.setV_max((String) jsonObjectForDto.get("V_MAX"));
                facilityDto.setRevstddaynm((String) jsonObjectForDto.get("REVSTDDAYNM"));
                facilityDto.setRevstdday((String) jsonObjectForDto.get("REVSTDDAY"));
                facilities.add(facilityDto);
            }
            
        } catch (Exception e) {
            log.error("Error fetching facilities", e);
        }

        return facilities;
    }

    public FacilityDto detailView(String id) {
        for (FacilityDto facility : facilities) {
            if (id.equals(facility.getSvcid())) {
                return facility;
            }
        }
        return null;
    }
    
}
