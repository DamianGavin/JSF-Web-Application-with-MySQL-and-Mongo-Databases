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
import com.geog.Model.Country;

@ApplicationScoped
@ManagedBean
public class CountryController {

	private SqlDAO db = new SqlDAO();
	private List<Country> countries = new ArrayList<>();
	private Country toUpdate;

	public CountryController() {
		// no arguments
	}

	public boolean codeIsValid(String code) {
		if (code.isEmpty()) { // code cannot be empty.
			return false;
		}
		if (code.length() > 3) { // code cannot be > 3 characters
			return false;
		}
		return true; // code was valid
	}

	public boolean nameIsValid(String name) {
		return !name.isEmpty();
	}

	public String deleteCountry(Country countryToDelete) {
		try {
			db.deleteCountry(countryToDelete);
		} catch (SQLException e) {
		}
		return null;
	}

	public String updateCountry(Country updatedCountry) {
		try {
			db.updateCountry(updatedCountry);
			return "list_country";
		} catch (SQLException e) {
			e.printStackTrace();
			return "update_country";
		}
	}

	public String navigateToUpdateCountry(Country country) {
		// save the country so we can see it on a different page.
		setToUpdate(country);
		return "update_country";
	}

	public String addCountry(Country countryToAdd) {
		// 1. check name should be not empty, > 0 and < 4
		boolean validCode = codeIsValid(countryToAdd.getCode());

		if (validCode == false) { // !validCode
			FacesContext.getCurrentInstance().addMessage("addCountryForm:invalidCountryCode",
					new FacesMessage("Country code is invalid!"));
			// display error
		}
		// 2. name must be not empty
		boolean validName = nameIsValid(countryToAdd.getName());
		if (!validName) {
			System.out.println("Invalid Name!");
			FacesContext.getCurrentInstance().addMessage("addCountryForm:invalidCountryName",
					new FacesMessage("Name is invalid!"));
		}
		// if the name or code isn't valid, stay on same page
		if (!(validName && validCode)) {
			return "add_country"; // stay on add country page.
		}
		// 3. description doesn't matter
		try {
			db.addCountry(countryToAdd);
			// only reach this line if there was no exception
			return "list_country"; // go back to the list countries page
		} catch (SQLIntegrityConstraintViolationException e) {
			// this exception happens when you try and add something to the db, but the db
			// won't allow it.
			// display message saying invalid country.
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Country code: " + countryToAdd.getCode() + " already exists!"));
			return "add_country.xhtml";

		} catch (SQLException e) {
			// display error
			return "add_country.xhtml";
		}
	}// add

	public void loadCountries() {
		try {
			countries = db.getCountries();
		} catch (SQLException e) {
			e.printStackTrace();
			countries = new ArrayList<>();
		}
	}// loadCountries

	public List<Country> getCountries() { // getter method
		return countries;
	}

	public Country getToUpdate() {
		return toUpdate;
	}

	public void setToUpdate(Country toUpdate) {
		this.toUpdate = toUpdate;
	}

}// CountryController
