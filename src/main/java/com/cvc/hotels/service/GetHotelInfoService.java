package com.cvc.hotels.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cvc.hotels.dto.HotelDTO;
import com.cvc.hotels.exception.HotelException;
import com.cvc.hotels.model.HotelInfo;
import com.cvc.hotels.model.PriceDetail;
import com.cvc.hotels.model.Room;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class GetHotelInfoService {

	@Value("${url.hotel.city}")
	private String urlHotelCity;

	@Value("${url.hotel.id}")
	private String urlHotelId;

	private RestTemplate restTemplate = new RestTemplate();
	private Gson gson = new Gson();

	@Autowired
	private PriceService priceService;

	public List<HotelInfo> getTotalPriceByCity(String cityCode, String checkin, String checkout, int adults, int child)
			throws HotelException {
		String url = urlHotelCity.concat(cityCode);
		List<HotelInfo> responseDomain = callCVCBackend(checkin, checkout, adults, child, url);

		return responseDomain;
	}

	public HotelInfo getTotalPriceByHotelId(String hotelId, String checkin, String checkout, int adults, int child)
			throws HotelException {
		String url = urlHotelId.concat(hotelId);
		List<HotelInfo> responseDomain = callCVCBackend(checkin, checkout, adults, child, url);

		return responseDomain.get(0);
	}

	private List<HotelInfo> callCVCBackend(String checkin, String checkout, int adults, int child, String url)
			throws HotelException {
		List<HotelDTO> backendResponse;
		try {
			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
			backendResponse = gson.fromJson(response.getBody(), new TypeToken<List<HotelDTO>>() {
			}.getType());
		} catch (Exception e) {
			throw new HotelException("Error while trying to call CVC Backend");
		}
		List<HotelInfo> responseDomain = buildResponse(backendResponse, checkin, checkout, adults, child);
		return responseDomain;
	}

	private List<HotelInfo> buildResponse(List<HotelDTO> backendResponse, String checkin, String checkout, int adults,
			int child) throws HotelException {
		List<HotelInfo> responseDomain = new ArrayList<HotelInfo>();
		if (backendResponse != null) {
			for (HotelDTO hotel : backendResponse) {
				HotelInfo hotelInfo = new HotelInfo();
				hotelInfo.setId(hotel.getId());
				hotelInfo.setCityName(hotel.getCityName());
				hotelInfo.setRooms(new ArrayList<Room>());
				for (com.cvc.hotels.dto.RoomDTO backendRoom : hotel.getRooms()) {
					Room room = new Room();
					room.setRoomID(backendRoom.getRoomID());
					room.setCategoryName(backendRoom.getCategoryName());
					if (backendRoom.getPrice() != null) {
						room.setTotalPrice(priceService.calculateTotalPrice(backendRoom.getPrice(), checkin, checkout,
								adults, child));
						room.setPriceDetail(new PriceDetail());
						room.getPriceDetail().setPricePerDayAdult(backendRoom.getPrice().getAdult());
						room.getPriceDetail().setPricePerDayChild(backendRoom.getPrice().getChild());
					}
					hotelInfo.getRooms().add(room);
				}
				responseDomain.add(hotelInfo);
			}
		}
		return responseDomain;
	}

}
