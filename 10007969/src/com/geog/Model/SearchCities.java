package com.geog.Model;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class SearchCities {
	private long population;
	private String countryCode;
	private boolean isCoastal;
	private String operand;

	public SearchCities() {
	}

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public boolean getIsCoastal() {
		return isCoastal;
	}

	public void setIsCoastal(boolean isCoastal) {
		this.isCoastal = isCoastal;
	}

	public String getoperand() {
		return operand;
	}

	public void setoperand(String operand) {
		this.operand = operand;
	}

}
