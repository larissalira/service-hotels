package com.cvc.hotels;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.cvc.hotels.model.HotelInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HotelsApplicationTests {

	@Test
	void contextLoads() {
	}

	public static String URL_PATH = "http://localhost:8080/";
	public static String URL_PATH_PRICEBYCITY = "totalPrice/city/";
	public static String URL_PATH_PRICEBYHOTELID = "totalPrice/hotel/";
	
	@Autowired
	private TestRestTemplate restTemplate;
	private JacksonTester<List<HotelInfo>> jsonResponseBuilderCity;
	private JacksonTester<HotelInfo> jsonResponseBuilderHotelId;
	

	@BeforeEach 
	public void setUp() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);
	}	
	
	@Test
	public void Success_ApiTotalPriceByCity() throws IOException, URISyntaxException {
		String cityCode = "1032";		
		String checkin = "12-12-2021";
		String checkout = "18-12-2021";
		String adults = "2";
		String child = "1";
		String url = URL_PATH.concat(URL_PATH_PRICEBYCITY).concat(cityCode);
		String urlFull = buildUrl(url, checkin, checkout, adults, child);
		ResponseEntity<String> responseEntity = callAPI(urlFull);
		List<HotelInfo> responsebody = jsonResponseBuilderCity.parseObject(responseEntity.getBody());
		assertNotNull(responsebody);
	}
	
	
	@Test
	public void Success_ApiTotalPriceByHotelId() throws IOException, URISyntaxException {
		String hotelId = "1032";		
		String checkin = "12-12-2021";
		String checkout = "18-12-2021";
		String adults = "2";
		String child = "1";		
		String url = URL_PATH.concat(URL_PATH_PRICEBYHOTELID).concat(hotelId);
		String urlFull = buildUrl(url, checkin, checkout, adults, child);
		ResponseEntity<String> responseEntity = callAPI(urlFull);
		HotelInfo responsebody = jsonResponseBuilderHotelId.parseObject(responseEntity.getBody());
		assertNotNull(responsebody);
	}

	private String buildUrl(String url, String checkin, String checkout, String adults, String child) throws URISyntaxException {
		URIBuilder builder = new URIBuilder(url);
		builder.addParameter("checkin", checkin);
		builder.addParameter("checkout", checkout);
		builder.addParameter("adults", adults);
		builder.addParameter("child", child);
		url = builder.build().toString();
		return url;
	}
	
	
	public ResponseEntity<String> callAPI(String urlFull)
			throws IOException {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> requestEntity = new HttpEntity<Object>(null, headers);
		ResponseEntity<String> responseBody = restTemplate.exchange(urlFull, HttpMethod.GET, requestEntity, String.class);
		
		return responseBody;
	}
	
	
}




	

