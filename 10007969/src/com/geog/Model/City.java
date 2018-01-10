package com.geog.Model;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class City {

	private String cityCode;
	private String coCode;
	private String regCode;
	private String cityName;
	private int population;
	private String isCoastal;
	private double areaKM;
	private String regionName;
	private String countryName;

	public City() {

	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCoCode() {
		return coCode;
	}

	public void setCoCode(String coCode) {
		this.coCode = coCode;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public double getAreaKM() {
		return areaKM;
	}

	public void setAreaKM(double areaKM) {
		this.areaKM = areaKM;
	}

	public String getIsCoastal() {
		return isCoastal;
	}

	public void setIsCoastal(String isCoastal) {
		this.isCoastal = isCoastal;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;

	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;

	}

	public String getRegionName() {
		return regionName;
	}

	public String getCountryName() {
		return countryName;
	}

}
