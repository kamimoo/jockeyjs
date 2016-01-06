package com.jockeyjs.converter;

public interface JsonConverter<T> {
	T fromJson(String v);
	String toJson(Object v);
}
