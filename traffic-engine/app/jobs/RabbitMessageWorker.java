package jobs;

import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import play.Logger;

public class RabbitMessageWorker implements Runnable {

	String message;
	
	public RabbitMessageWorker(String message) {
		this.message = message;
	}
	
	@Override
	public void run() {
		
		JSONParser jsop = new JSONParser();
//		JSONObject jobj = new JSONObject();

		try {
			JSONObject jobj = (JSONObject) jsop.parse(message);
			JSONObject location = (JSONObject) jobj.get("pickupLocation");
			Double lat = (Double) location.get("lat");
			Double lon = (Double) location.get("lon");
			System.out.println("lat = "+ lat + " lan = "+ lon + "");
		} catch (ParseException e) {
			Logger.error("Error parsing message in RabbitMessageWorker - "+ e.toString());
		}
	}

}
