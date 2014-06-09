package models;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import play.Logger;
import play.Play;

public class RabbitMQClient {

	private String host;
	private String port;
	private String user;
	private String pass;
	private String rideQueue;
	private String rideExchange;
//	public String exchangeType[] = {"direct", "fanout"};
	
	private void init() {
		host = Play.configuration.getProperty("rabbit.host");
		port = Play.configuration.getProperty("rabbit.port");
		user = Play.configuration.getProperty("rabbit.username");
		pass = Play.configuration.getProperty("rabbit.password");
	}
	
	private void getRideRequestClient() {
		init();
		rideQueue = Play.configuration.getProperty("tp.request-queue");
		rideExchange = Play.configuration.getProperty("tp.request-exchange");
	}
	
	public Channel getRideRequestChannel() {
		getRideRequestClient();
		String uri = "amqp://" + user + ":" + pass + "@" + host + ":" + port;
		Channel channel = null;
		ConnectionFactory factory = new ConnectionFactory();
		try {
			factory.setUri(uri);
		    Connection connection = factory.newConnection();
		    channel = connection.createChannel();
//		    channel.exchangeDeclare(rideExchange, "fanout");
		    rideQueue = channel.queueDeclare().getQueue();
	        channel.queueBind(rideQueue, rideExchange, "");
		}
		 catch(Exception ex) {
		    	Logger.error("Error in RabbitMQClient while creating Client - "+ ex.toString());
		 }
		return channel;
	}

	/**
	 * @return the rideQueue
	 */
	public String getRideQueue() {
		return rideQueue;
	}

	/**
	 * @param rideQueue the rideQueue to set
	 */
	public void setRideQueue(String rideQueue) {
		this.rideQueue = rideQueue;
	}
}
