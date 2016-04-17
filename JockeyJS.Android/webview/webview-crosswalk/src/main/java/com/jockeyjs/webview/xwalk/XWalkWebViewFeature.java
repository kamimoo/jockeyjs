package com.jockeyjs.webview.xwalk;

import com.jockeyjs.JockeyImpl;
import com.jockeyjs.webview.WebViewFeature;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

public class XWalkWebViewFeature implements WebViewFeature {
	private final XWalkView xWalkView;
	private final XWalkResourceClient resourceClient;

	public XWalkWebViewFeature(XWalkView xWalkView) {
		this(xWalkView, null);
	}

	public XWalkWebViewFeature(XWalkView xWalkView, XWalkResourceClient resourceClient) {
		this.xWalkView = xWalkView;
		this.resourceClient = resourceClient;
	}

	@Override
	public void bindJockey(JockeyImpl jockey) {
		JockeyXWalkResourceClient client = new JockeyXWalkResourceClient(xWalkView, jockey);
		xWalkView.setResourceClient(client);
	}

	@Override
	public void evaluateJavascript(String url) {
		xWalkView.evaluateJavascript(url, null);
	}
}
