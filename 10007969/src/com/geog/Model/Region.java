package com.geog.Model;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Region {

	private String code;
	private String regCode;
	private String regName;
	private String regDescription;

	public Region() {

	}// region()

	public Region(String code, String regCode, String regName, String regDescription) {
		super();
		this.code = code;
		this.regCode = regCode;
		this.regName = regName;
		this.regDescription = regDescription;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getRegDescription() {
		return regDescription;
	}

	public void setRegDescription(String regDescription) {
		this.regDescription = regDescription;
	}

}// class
