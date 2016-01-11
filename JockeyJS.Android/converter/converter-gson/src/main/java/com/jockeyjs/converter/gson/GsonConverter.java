package com.jockeyjs.converter.gson;

import com.google.gson.Gson;
import com.jockeyjs.JockeyWebViewPayload;
import com.jockeyjs.converter.JsonConverter;

public class GsonConverter implements JsonConverter<JockeyWebViewPayload> {
	private Gson gson = new Gson();

	@Override
	public JockeyWebViewPayload fromJson(String v) {
		return gson.fromJson(v, JockeyWebViewPayload.class);
	}

	@Override
	public String toJson(Object v) {
		return gson.toJson(v);
	}
}
