package com.geog.Model;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class FindCity {
	private int population;
	private String countryCode;
	private String isCoastal;
	private String operand;

	public FindCity() {

	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getIsCoastal() {
		return isCoastal;
	}

	public void setIsCoastal(String isCoastal) {
		this.isCoastal = isCoastal;
	}

	public String getOperand() {
		return operand;
	}

	public void setOperand(String operand) {
		this.operand = operand;
	}

}
