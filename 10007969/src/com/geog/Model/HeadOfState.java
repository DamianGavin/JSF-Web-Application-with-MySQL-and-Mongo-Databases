package com.geog.Model;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class HeadOfState {

	private String _id;
	private String headOfState;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getHeadOfState() {
		return headOfState;
	}

	public void setHeadOfState(String headOfState) {
		this.headOfState = headOfState;
	}

}
