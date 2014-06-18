package models;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import play.Logger;
import play.Play;

public class MongoClient {

	private String host;
	private int port;
	private String dbName;
	
	private void init() {
		host = Play.configuration.getProperty("mongo.host");
		port = Integer.parseInt(Play.configuration.getProperty("mongo.port"));
		dbName = Play.configuration.getProperty("mongo.name");
	}
	
	private Mongo getMongoClient() {
		Mongo mongo = null;
		init();
		try {
			mongo = new Mongo(host, port);
		} catch (UnknownHostException e) {
			Logger.error("Error in MongoClient.java - " + e.toString());
			e.printStackTrace();
		} catch (MongoException ex) {
			Logger.error("Error in MongoClient.java - " + ex.toString());
		}
		return mongo;
	}
	
	private DB getMongoDB() {
		Mongo mongo = getMongoClient();
		DB db = mongo.getDB(dbName);
		return db;
	}
	
	public void showRecords() {
		DB db = getMongoDB();
		DBCollection lUpd = db.getCollection("location_updates");
		
		System.out.println("Total records found: " + lUpd.getCount());
		
/*		DBCursor cursor = lUpd.find();
		try {
		   while(cursor.hasNext()) {
		       System.out.println(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
*/
		List<DBObject> list = lUpd.getIndexInfo();

		for (DBObject o : list) {
		   System.out.println(o.get("key"));
		}
	}
}
