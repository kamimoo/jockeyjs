package com.jockeyjs.webview;

import com.jockeyjs.JockeyImpl;

public interface WebViewFeature {
	void bindJockey(JockeyImpl jockey);

	void evaluateJavascript(String url);
}
