package com.company.transportmanagementuser.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.transportmanagementuser.model.Request;
import com.company.transportmanagementuser.model.Vehicle;
import com.company.transportmanagementuser.repository.UserServiceRepository;

@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	UserServiceRepository userServiceRepository;

	public List<Vehicle> getVehicleList(Request request) throws Exception {
		Date date = null;
		List<Vehicle> vehicles = null;
		try {
			date = format.parse(request.getRouteDate());
			vehicles = userServiceRepository.getVehicleListByRoute(request.getRoute().getRouteId(), date);
			LOGGER.info("Vehicles List obtained for route : " + request.getRoute().getRouteId() + " are" + vehicles);
		} catch (ParseException e) {
			LOGGER.error("ParseException :: Invalid route-date recived " + e);
			throw new Exception("Exception :: Invalid route-date recived");
		} catch (NullPointerException e) {
			LOGGER.error("NullPointerException :: route id or route_date is missing ");
			throw new Exception("Exception : route id or route_date is missing ");
		} catch (Exception e) {
			LOGGER.error("Exception :: " + e);
			throw new Exception(e);
		}
		return vehicles;
	}

	public boolean checkSeat(Request request) throws Exception {
		Date date = null;
		int availableSeat = 0;
		try {
			date = format.parse(request.getRouteDate());
			availableSeat = userServiceRepository.checkSeat(request.getRoute().getRouteId(),
					request.getVehicle().getVehicleId(), date);
			LOGGER.info("available seats : " + availableSeat);
		} catch (ParseException e) {
			LOGGER.error("Exception Invalid route-date recived " + e);
			throw new Exception("Invalid route-date recived");

		} catch (NullPointerException e) {
			LOGGER.error("Exception route id,vehicle id or route_date is missing ");
			throw new Exception("Exception route id,vehicle id or route_date is missing ");
		}
		if (availableSeat > 0) {
			return true;
		} else {
			return false;
		}

	}

}
