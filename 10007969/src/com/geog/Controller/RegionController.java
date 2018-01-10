package com.geog.Controller;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.geog.Model.Region;
import com.geog.DAO.SqlDAO;

@ApplicationScoped
@ManagedBean
public class RegionController {

	private SqlDAO db = new SqlDAO();
	private List<Region> regions = new ArrayList<>();

	public RegionController() {
		db = new SqlDAO();
	}

	public List<Region> loadRegions() throws SQLException {
		regions = db.getRegions();

		return regions;
	}

	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
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

	public String addRegion(Region regionToAdd) {

		// 1. check name should be not empty, > 0 and < 4
		boolean validCode = codeIsValid(regionToAdd.getCode());

		if (validCode == false) { // !validCode
			FacesContext.getCurrentInstance().addMessage("addRegionForm:invalidCode",
					new FacesMessage("Country code is invalid!"));
		}

		boolean validRegionCode = codeIsValid(regionToAdd.getRegCode());

		if (validRegionCode == false) { // !validCode
			FacesContext.getCurrentInstance().addMessage("addRegionForm:invalidRegionCode",
					new FacesMessage("Region code is invalid!"));
		}

		// 2. name must be not empty
		boolean validName = nameIsValid(regionToAdd.getRegName());
		if (!validName) {
			FacesContext.getCurrentInstance().addMessage("addRegionForm:invalidRegName",
					new FacesMessage("Name is invalid!"));
		}

		// if the name or code isn't valid, stay on same page
		if (!(validName && validCode)) {
			return "add_region"; // stay on add region page.
		}
		// 3. description doesn't matter

		try {
			db.addRegion(regionToAdd);
			// only reach this line if there was no exception
			return "list_region.xhtml"; // go back to the list region page
		} catch (SQLIntegrityConstraintViolationException e) {
			// this exception happens when you try and add something to the db, but the db
			// won't allow it.
			// display message saying invalid country.
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(" Error. Country " + regionToAdd.getCode() + " does not exist!"));
			return null;

		} catch (SQLException e) {
			// display error
			e.printStackTrace();
			return "add_region.xhtml";
		}
	}

}// Region Controller
