package com.company.transportmanagementuser.repository;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.company.transportmanagementuser.extractor.VehicleListExtractor;
import com.company.transportmanagementuser.model.Vehicle;

@Repository
public class UserServiceRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceRepository.class);

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	NamedParameterJdbcTemplate jdbcTemplate;

	public List<Vehicle> getVehicleListByRoute(int routeId, Date date) {
		LOGGER.info("fetch vehicles for route id : " + routeId + " and route date : " + date);
		String selectVehicleQuery = "SELECT   v.vehicle_id,v.vehicle_name,v.registration_number,v.vehicle_type,v.capacity,v.health_status,v.created_date,v.updated_date\r\n"
				+ "FROM VEHICLE_ROUTE_MAP vrm  INNER JOIN VEHICLE v  on vrm.vehicle_id=v.vehicle_id\r\n"
				+ "where vrm.route_date = :route_date AND vrm.route_id=:route_id";

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("route_id", routeId, Types.INTEGER).addValue("route_date", date, Types.DATE);
		LOGGER.info("select vehicle paramSource " + paramSource);
		List<Vehicle> vehicleList = jdbcTemplate.query(selectVehicleQuery, paramSource, new VehicleListExtractor());
		LOGGER.info("Vehicle List recieved : " + vehicleList);
		return vehicleList;
	}

	public int checkSeat(int routeId, int vehicleId, Date date) throws Exception {
		LOGGER.info("check seat avilable for vehicle :" + vehicleId + " & route  : " + routeId + " on route date : "
				+ date);
		String selectSeatAvailableQuery = "SELECT  seat_available FROM VEHICLE_ROUTE_MAP \r\n"
				+ "WHERE route_date = :route_date AND route_id=:route_id AND vehicle_id=:vehicle_id";
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("route_id", routeId, Types.INTEGER).addValue("route_date", date, Types.DATE)
				.addValue("vehicle_id", vehicleId, Types.INTEGER);
		int availableSeats = 0;
		try {
			LOGGER.info("select seat availablity paramSource " + paramSource);
			availableSeats = jdbcTemplate.queryForObject(selectSeatAvailableQuery, paramSource, Integer.class);
		} catch (DataAccessException accessException) {
			LOGGER.error("Exception ::  vehicle :" + vehicleId + " not found on route : " + routeId + " at " + date
					+ " :: " + accessException);
			throw new Exception(
					"Exception ::  vehicle :" + vehicleId + " not found on route : " + routeId + " at " + date);
		}
		return availableSeats;

	}

}
