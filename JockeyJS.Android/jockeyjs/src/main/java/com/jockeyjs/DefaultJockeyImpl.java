package com.jockeyjs;

public class DefaultJockeyImpl extends JockeyImpl {
	
	private int messageCount = 0;

	@Override
	public void send(String type, Object withPayload,
			JockeyCallback complete) {
		int messageId = messageCount;

		if (complete != null) {
			add(messageId, complete);
		}

		if (withPayload != null) {
			withPayload = _converter.toJson(withPayload);
		}

		String url = String.format("javascript:Jockey.trigger(\"%s\", %d, %s)",
				type, messageId, withPayload);
		_feature.evaluateJavascript(url);

		++messageCount;
	}

	@Override
	public void triggerCallbackOnWebView(int messageId) {
		String url = String.format("javascript:Jockey.triggerCallback(\"%d\")",
				messageId);
		_feature.evaluateJavascript(url);
	}

}
