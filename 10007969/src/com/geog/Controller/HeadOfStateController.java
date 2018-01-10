package com.geog.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.geog.DAO.MongoDAO;
import com.geog.Model.HeadOfState;

@ApplicationScoped
@ManagedBean
public class HeadOfStateController {

	private MongoDAO mongo = new MongoDAO();
	private List<HeadOfState> headsOfState = new ArrayList<>();

	public List<HeadOfState> getHeadsOfState() {
		return headsOfState;
	}

	public void setHeadsOfState(List<HeadOfState> headsOfState) {
		this.headsOfState = headsOfState;
	}

	public void loadHeadsOfState() {
		// loads in the heads of state from the mongo database.
		headsOfState = mongo.getHeadsOfState();
	}

	public void deleteHeadOfState(HeadOfState headOfState) {
		mongo.deleteHeadOfState(headOfState);
	}

	public String updateHeadOfState(HeadOfState updatedHeadOfState) {
		mongo.updateHeadOfState(updatedHeadOfState);
		return "list_heads_of_state";
	}

}
