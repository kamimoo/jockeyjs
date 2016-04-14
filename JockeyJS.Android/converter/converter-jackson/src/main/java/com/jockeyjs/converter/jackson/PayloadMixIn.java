package com.jockeyjs.converter.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jockeyjs.JockeyWebViewPayload;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
class PayloadMixIn extends JockeyWebViewPayload {

	@JsonProperty
	public void setId(int id) {
		this.id = id;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonProperty("payload")
	@JsonDeserialize(as = HashMap.class)
	public void setPayload(HashMap<Object, Object> payload) {
		this.payload = payload;
	}
}
