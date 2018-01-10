package com.geog.Controller;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.geog.DAO.SqlDAO;
import com.geog.Model.City;
import com.geog.Model.SearchCities;

@ApplicationScoped
@ManagedBean
public class CityController {

	private SqlDAO db = new SqlDAO();
	private List<City> cities = new ArrayList<>();
	private List<City> searchCities = new ArrayList<>();

	// ===========================================================================
	private City selected;// saves variable.

	public City getSelected() {
		return selected;
	}

	public void setSelected(City selected) {
		this.selected = selected;
	}
	// ===========================================================================

	public CityController() {// constructor no arguments
	}

	public String getDetailsFor(City city) {
		// save the city so we can see it on a different page.
		setSelected(city);
		return "displayAllCity";
	}

	public void loadCities() {// builds up the list of cities from database
		try {
			cities = db.getCities();
		} catch (SQLException e) {
			e.printStackTrace();
			cities = new ArrayList<>();
		}

	}// loadCities returns the list of cities

	public List<City> getCities() {
		return cities;
	}

	// ===========================================================================
	public boolean codeIsValid(String cityCode) {
		if (cityCode.isEmpty()) { // code cannot be empty.
			return false;
		}
		if (cityCode.length() > 3) { // code cannot be > 3 characters
			return false;
		}
		return true; // code was valid
	}// codeIsValid

	public boolean nameIsValid(String name) {
		return !name.isEmpty();
	}

	public String addCity(City cityToAdd) {
		// check code should be not empty, > 0 and < 4
		boolean validCityCode = codeIsValid(cityToAdd.getCityCode());

		if (validCityCode == false) { // !validCode
			FacesContext.getCurrentInstance().addMessage("addCityForm:invalidCityCode",
					new FacesMessage(" City code is invalid!"));// display error
		}

		// validate country code
		boolean validCountryCode = codeIsValid(cityToAdd.getCoCode());
		if (validCountryCode == false) {
			FacesContext.getCurrentInstance().addMessage("addCityForm:invalidCoCode",
					new FacesMessage(" Country code is invalid!"));// display error
		}

		// validate region code
		boolean validRegionCode = codeIsValid(cityToAdd.getRegCode());
		if (validRegionCode == false) {
			FacesContext.getCurrentInstance().addMessage("addCityForm:invalidRegCode",
					new FacesMessage(" Region code is invalid!"));// display error
		}

		// validate name
		// if the name is NOT empty, then it is a valid name
		boolean validName = !cityToAdd.getCityName().trim().isEmpty();
		if (!validName) {
			FacesContext.getCurrentInstance().addMessage("addCityForm:invalidCityName",
					new FacesMessage(" City name is invalid!"));// display error
		}

		try {
			db.addCity(cityToAdd);
			// If we get here there was no exception
			return "list_city"; // revert to the list_city page
		} catch (SQLIntegrityConstraintViolationException e) {
			FacesContext.getCurrentInstance().addMessage(null, // null to make it a global message
					new FacesMessage(" Error adding City: " + cityToAdd.getCountryName() + " to region: "
							+ cityToAdd.getRegCode() + " in country: " + cityToAdd.getCoCode()));// display error
			return null;
		} catch (SQLException e) {
			// display error
			e.printStackTrace();
			return "add_city";
		}

	}// addCity

	// This method is required for "Find All Cities"
	public String searchForCities(SearchCities search) {
		try {
			setSearchCities(db.searchCities(search));
			return "search_results";
		} catch (SQLException e) {
			return null;
		}
	}

	public List<City> getSearchCities() {
		return searchCities;
	}

	public void setSearchCities(List<City> searchCities) {
		this.searchCities = searchCities;
	}

}// CityController
