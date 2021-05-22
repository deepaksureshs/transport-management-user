package com.company.transportmanagementuser.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.company.transportmanagementuser.model.Vehicle;

public class VehicleListExtractor implements ResultSetExtractor<List<Vehicle>> {

	@Override
	public List<Vehicle> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Vehicle> vehicleList = new ArrayList<Vehicle>();
		while (rs.next()) {
			Vehicle vehicle = new Vehicle();
			vehicle.setVehicleId(rs.getInt("v.vehicle_id"));
			vehicle.setVehicleName(rs.getString("v.vehicle_name"));
			vehicle.setRegistrationNumber(rs.getString("v.registration_number"));
			vehicle.setVehicleType(rs.getString("v.vehicle_type"));
			vehicle.setCapacity(rs.getInt("v.capacity"));
			vehicle.setHealthStatus(rs.getString("v.health_status"));
			vehicleList.add(vehicle);
		}
		return vehicleList;
	}

}
