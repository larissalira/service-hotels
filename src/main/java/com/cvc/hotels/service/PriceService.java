package com.cvc.hotels.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cvc.hotels.dto.PriceDTO;
import com.cvc.hotels.exception.HotelException;

@Service
public class PriceService {

	private static final String DATE_FORMAT = "dd-MM-yyyy";
	
	@Value("${comission.value}")
	private double comission;

	public double calculateTotalPrice(PriceDTO price, String checkin, String checkout, double adults, double child)
			throws HotelException {
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		double totalDays = getDifferenceDays(checkin, checkout);
		double totalPriceAdults = (price.getAdult() * totalDays * adults) / comission;
		double totalPriceChild = (price.getChild() * totalDays * child) / comission;
		double totalPrice = totalPriceAdults + totalPriceChild;
		String formartedPrice = new DecimalFormat("#.##").format(totalPrice).replace(",", ".");
		return Double.valueOf(formartedPrice);

	}

	private static int getDifferenceDays(String checkin, String checkout) throws HotelException {
		Date dateCheckin;
		Date dateCheckout;
		long diff = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			dateCheckin = sdf.parse(checkin);
			dateCheckout = sdf.parse(checkout);
			diff = dateCheckout.getTime() - dateCheckin.getTime();
			return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			throw new HotelException("Error while trying to format date");
		}
	}
}
