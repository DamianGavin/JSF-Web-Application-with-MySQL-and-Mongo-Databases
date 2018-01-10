package com.geog.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.geog.Model.City;
import com.geog.Model.Country;
import com.geog.Model.Region;
import com.geog.Model.SearchCities;

public class SqlDAO {

	private Connection connection;

	public SqlDAO() {
		connect();
	}

	// =========================================================region=============================================================================
	public List<Region> getRegions() throws SQLException {
		String queryString = "SELECT * FROM REGION";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(queryString);

		List<Region> regions = new ArrayList<>();
		while (rs.next()) {
			Region region = new Region();
			region.setCode(rs.getString("co_code"));
			region.setRegCode(rs.getString("reg_code"));
			region.setRegName(rs.getString("reg_name"));
			region.setRegDescription(rs.getString("reg_desc"));
			regions.add(region);
		}
		return regions;
	}

	public void addRegion(Region regionToAdd) throws SQLException {
		// INSERT INTO Region VALUES("123", "A dumb Name", "a dumb description");
		String query = "INSERT INTO REGION VALUES(?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, regionToAdd.getCode());
		statement.setString(2, regionToAdd.getRegCode());
		statement.setString(3, regionToAdd.getRegName());
		statement.setString(4, regionToAdd.getRegDescription());
		statement.executeUpdate();
	}

	public void updateRegion(Region regionToUpdate) throws SQLException {
		// UPDATE Region SET co_name=?, co_details=? WHERE co_code=?
		String query = "UPDATE REGION SET reg_code=?, reg_name=?, reg_desc=? WHERE co_code=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, regionToUpdate.getCode());
		statement.setString(2, regionToUpdate.getRegName());
		statement.setString(3, regionToUpdate.getRegDescription());
		statement.executeUpdate();
	}

	// ============================== Country ======================================================================
	
	public void addCountry(Country countryToAdd) throws SQLException {
		// INSERT INTO COUNTRY VALUES("123", "A dumb Name", "a dumb description");
		String query = "INSERT INTO COUNTRY VALUES(?,?,?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, countryToAdd.getCode());
		statement.setString(2, countryToAdd.getName());
		statement.setString(3, countryToAdd.getDetails());
		statement.executeUpdate();
	}

	public void updateCountry(Country countryToUpdate) throws SQLException {
		// UPDATE COUNTRY SET co_name=?, co_details=? WHERE co_code=?
		String query = "UPDATE COUNTRY SET co_name=?, co_details=? WHERE co_code=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, countryToUpdate.getName());
		statement.setString(2, countryToUpdate.getDetails());
		statement.setString(3, countryToUpdate.getCode());
		statement.executeUpdate();
	}

	public void deleteCountry(Country countryToDelete) throws SQLException {

		// DELETE FROM COUNTRY WHERE CO_CODE = "123";
		String query = "DELETE FROM COUNTRY WHERE CO_CODE = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, countryToDelete.getCode());
		statement.executeUpdate();

	}

	public List<Country> getCountries() throws SQLException {

		String queryCountry = "SELECT * FROM country";// sql query
		Statement statement = connection.createStatement(); // SQLException might happen here
		ResultSet rs = statement.executeQuery(queryCountry); // represents results from the SQL query

		List<Country> countries = new ArrayList<>(); // make a list to hold the Country objects
		while (rs.next()) {
			Country country = new Country();// new country object with all values as null
			// co_code, co_name, co_details have to exactly follow the names in sql database
			String codeFromDb = rs.getString("co_code"); // extract value from result set
			// set the country object code to that value
			country.setCode(codeFromDb);// getString - A String that contains the column name, Return Value - string
			country.setName(rs.getString("co_name"));
			country.setDetails(rs.getString("co_details"));
			countries.add(country);// add to the countries Array List
		}

		return countries;// return the list
	}// getCountries

	//===========================================City=======================================================
	
	public List<City> getCities() throws SQLException {

		// String queryCity = "SELECT * FROM city";
		String queryCity = "SELECT * FROM city " + "INNER JOIN country on country.co_code = city.co_code "
				+ "INNER JOIN region ON region.reg_code = city.reg_code";

		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(queryCity);

		List<City> cities = new ArrayList<>();
		while (rs.next()) {
			City city = new City();
			// cty_code | co_code | reg_code | cty_name | population | isCoastal | areaKM |

			city.setCityCode(rs.getString("cty_code"));
			city.setCoCode(rs.getString("co_code"));
			city.setRegCode(rs.getString("reg_code"));
			city.setCityName(rs.getString("cty_name"));
			city.setPopulation(rs.getInt("population"));
			city.setIsCoastal(rs.getString("isCoastal"));
			city.setAreaKM(rs.getDouble("areaKM"));
			// from region table
			city.setRegionName(rs.getString("reg_name"));
			// from country table
			city.setCountryName(rs.getString("co_name"));

			cities.add(city);
		}

		return cities;
	} // getCities

	// Adding the city
	public void addCity(City cityToAdd) throws SQLException {
		// INSERT INTO COUNTRY VALUES("123", "A dumb Name", "a dumb description");
		String query = "INSERT INTO CITY VALUES(?,?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, cityToAdd.getCityCode());
		statement.setString(2, cityToAdd.getCoCode());
		statement.setString(3, cityToAdd.getRegCode());
		statement.setString(4, cityToAdd.getCityName());
		statement.setInt(5, cityToAdd.getPopulation());
		statement.setString(6, cityToAdd.getIsCoastal());
		statement.setDouble(7, cityToAdd.getAreaKM());

		statement.executeUpdate();
	}

	// ================================== Connect=========================================
	private void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/geography", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// connect

	// ================================= Search Cities ================================
	 

	private String getMapping(SearchCities search) {
		String operand = search.getoperand(); // "e", "gt", "lt"
		switch (operand) {
		case "e":
			return "=";
		case "lt":
			return "<";
		case "gt":
			return ">";
		default:
			throw new IllegalArgumentException("Bad input."); // programmer made a mistake, since we provide all
																// possible options.
		}
	}

	// =============== SEARCH STUFF ====================
	public List<City> searchCities(SearchCities search) throws SQLException {

		// 1. convert from e, gt, le -> =, >, <
		String sqlSymbol = getMapping(search);

		String chosenCountry = search.getCountryCode();
		if (chosenCountry.trim().isEmpty()) { // check for country code
			// don't search country
			chosenCountry = "%";// search EVERY country
		}

		String isCoastalStr;
		if (search.getIsCoastal()) {
			isCoastalStr = "TRUE";
		} else {
			isCoastalStr = "FALSE";
		}

		boolean lookingAtPopulation = true;
		if (search.getPopulation() == 0) { // check for population
			// don't search population
			lookingAtPopulation = false;
		}

		// build query
		String baseQuery = "SELECT * FROM CITY INNER JOIN REGION ON REGION.REG_CODE = CITY.REG_CODE INNER JOIN COUNTRY ON COUNTRY.CO_CODE = REGION.CO_CODE WHERE ";
		String fullQuery = baseQuery;
		if (lookingAtPopulation) {
			fullQuery += "POPULATION " + sqlSymbol + " ? AND ";
		}
		fullQuery += "ISCOASTAL LIKE ? AND CITY.CO_CODE LIKE ?";

		PreparedStatement stmt = connection.prepareStatement(fullQuery);
		if (lookingAtPopulation) { // 3 question marks
			stmt.setLong(1, search.getPopulation()); // will be > 0
			stmt.setString(2, isCoastalStr); // will be TRUE or FALSE
			stmt.setString(3, chosenCountry); // may be USA/FRA or %
		} else { // 2 question marks
			stmt.setString(1, isCoastalStr); // will be TRUE or FALSE
			stmt.setString(2, chosenCountry); // may be USA/FRA or %
		}

		ResultSet rs = stmt.executeQuery();
		List<City> cities = new ArrayList<>();
		while (rs.next()) {
			final City city = new City();
			city.setCityCode(rs.getString("cty_code"));
			city.setAreaKM(rs.getDouble("areaKM"));
			city.setIsCoastal(rs.getString("isCoastal"));
			city.setCoCode(rs.getString("co_code"));
			city.setCityName(rs.getString("cty_name"));
			city.setPopulation(rs.getInt("population"));
			city.setRegCode(rs.getString("reg_code"));
			city.setRegionName(rs.getString("reg_name"));
			city.setCountryName(rs.getString("co_name"));
			cities.add(city);
		}
		return cities;
	}

}// SqlDAO
