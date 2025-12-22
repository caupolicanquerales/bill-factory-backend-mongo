package com.example.facturas_sinteticas_mongo.service.utils;

import org.springframework.http.codec.ServerSentEvent;

import com.example.facturas_sinteticas_mongo.response.DataMessage;

public class ConverterUtil {
	
	
	public static DataMessage setDataMessage(String data) {
		DataMessage dataMessage= new DataMessage();
		dataMessage.setMessage(data);
		return dataMessage;
	}
	
	public static ServerSentEvent<DataMessage> setServerSentEvent(DataMessage data, String eventName) {
		return ServerSentEvent.<DataMessage>builder()
				.id("1")
				.comment("prompt image fue grabado")
				.event(eventName)
			    .data(data)
			    .build();
	}
}
