package com.jockeyjs.webview;

import com.jockeyjs.JockeyImpl;
import com.jockeyjs.converter.JsonConverter;

public interface WebViewFeature {
	void bindJockey(JockeyImpl jockey, JsonConverter converter);

	void evaluateJavascript(String url);
}
