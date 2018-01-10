package com.geog.DAO;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.geog.Model.HeadOfState;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDAO {

	private MongoDatabase database;

	public MongoDAO() {
		MongoClient client = new MongoClient();
		database = client.getDatabase("headsOfStateDB");
	}

	public List<HeadOfState> getHeadsOfState() {
		// make a new list to hold heads of state
		List<HeadOfState> headsOfState = new ArrayList<>();
		// get the collection from the data base
		MongoCollection<Document> documents = database.getCollection("headsOfState");
		// getting the results of a "find" command
		FindIterable<Document> iterable = documents.find();
		Gson gson = new Gson(); // need to convert from json to HeadOfState object
		for (Document doc : iterable) {
			HeadOfState headOfState = gson.fromJson(doc.toJson(), HeadOfState.class);
			headsOfState.add(headOfState);
		}
		return headsOfState;
	}

	public void deleteHeadOfState(HeadOfState headOfState) {
		// get the collection to delete from
		MongoCollection<Document> documents = database.getCollection("headsOfState");
		// delete the corresponding head of state.
		documents.deleteOne(new Document("_id", headOfState.get_id())); // "{_id : 'some val'}"
	}

	public void updateHeadOfState(HeadOfState headOfStateToUpdate) {
		MongoCollection<Document> documents = database.getCollection("headsOfState");

	}
}
