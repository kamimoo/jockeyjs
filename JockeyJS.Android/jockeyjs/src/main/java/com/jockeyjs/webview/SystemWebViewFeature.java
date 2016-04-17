package com.jockeyjs.webview;

import android.annotation.SuppressLint;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.jockeyjs.JockeyImpl;
import com.jockeyjs.JockeyWebViewClient;

public class SystemWebViewFeature implements WebViewFeature {
	private final WebView webView;
	private final WebViewClient webViewClient;

	public SystemWebViewFeature(WebView webView) {
		this(webView, null);
	}

	public SystemWebViewFeature(WebView webView, WebViewClient webViewClient) {
		this.webView = webView;
		this.webViewClient = webViewClient;
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void bindJockey(JockeyImpl jockey) {
		webView.getSettings().setJavaScriptEnabled(true);
		JockeyWebViewClient jockeyWebViewClient = new JockeyWebViewClient(jockey);
		jockeyWebViewClient.setDelegate(webViewClient);
		webView.setWebViewClient(jockeyWebViewClient);
	}

	@Override
	public void evaluateJavascript(String url) {
		webView.loadUrl(url);
	}
}
