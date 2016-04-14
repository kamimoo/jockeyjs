package com.jockeyjs.converter.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jockeyjs.JockeyWebViewPayload;
import com.jockeyjs.converter.JsonConverter;
import java.io.IOException;

public class JacksonConverter implements JsonConverter<JockeyWebViewPayload> {

	private final ObjectMapper mapper;

	public JacksonConverter() {
		mapper = new ObjectMapper()
			.addMixIn(JockeyWebViewPayload.class, PayloadMixIn.class);
	}

	@Override
	public JockeyWebViewPayload fromJson(String v) {
		JockeyWebViewPayload payload = null;
		try {
			payload = mapper.readValue(v, JockeyWebViewPayload.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return payload;
	}

	@Override
	public String toJson(Object v) {
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(v);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
