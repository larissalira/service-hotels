package com.cvc.hotels.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cvc.hotels.exception.HotelException;
import com.cvc.hotels.model.HotelInfo;
import com.cvc.hotels.service.GetHotelInfoService;


@RestController
@RequestMapping("/totalPrice")
public class HotelsController {

	@Autowired
	GetHotelInfoService service;
	
	@GetMapping("/city/{cityCode}")
	public List<HotelInfo> getReservationByCity( 
			@PathVariable(required = true) String cityCode,
			@RequestParam(required = true) String checkin,
			@RequestParam(required = true) String checkout,
			@RequestParam(required = true) int adults,
			@RequestParam(required = true) int child) throws HotelException {
		return service.getTotalPriceByCity(cityCode, checkin, checkout, adults, child);

	}
	
	@GetMapping("/hotel/{hotelId}")
	public HotelInfo getTotalPriceByHotel(
			@PathVariable(required = true) String hotelId,
			@RequestParam(required = true) String checkin,
			@RequestParam(required = true) String checkout,
			@RequestParam(required = true) int adults,
			@RequestParam(required = true) int child) throws HotelException {
		return service.getTotalPriceByHotelId(hotelId, checkin, checkout, adults, child);
	}
	
	
	@ExceptionHandler({HotelException.class})
    protected ResponseEntity<Map<String, Object>> exceptionHandler(HotelException e) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("message:", e.getMessage());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }
}
