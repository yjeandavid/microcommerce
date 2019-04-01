package com.ecommerce.microcommerce.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.microcommerce.model.Event;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Service
public class PublisherImpl implements Publisher {
	private static final Logger LOG = Logger.getLogger(PublisherImpl.class);

	private static final String EXCHANGE_NAME = "alerts";

	@Value("${rabbitmq.hostname}")
	private String hostRabbitMQName;

	private ConnectionFactory factory;
	
	public PublisherImpl() throws IOException, TimeoutException {
		factory = new ConnectionFactory();
		factory.setHost(hostRabbitMQName);
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		} catch (IOException | TimeoutException ex) {
			LOG.error(ex.getMessage(), ex);
		}
	}

	@Override
	public void publishEvent(Event event) {
		try (Connection connection = factory.newConnection();
				Channel channel = connection.createChannel()) {
			// channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			channel.basicPublish(EXCHANGE_NAME, "", null, event.toJSON().getBytes("UTF-8"));
			LOG.info(String.format("Message envoyée : %s", event.toJSON()));
		} catch (IOException | TimeoutException ex) {
			LOG.error(ex.getMessage(), ex);
		}
	}

}
