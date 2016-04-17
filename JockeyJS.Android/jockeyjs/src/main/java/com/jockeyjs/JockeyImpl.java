/*******************************************************************************
 * Copyright (c) 2013,  Paul Daniels
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
package com.jockeyjs;

import android.net.Uri;
import com.jockeyjs.converter.JsonConverter;
import com.jockeyjs.webview.WebViewFeature;
import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.util.SparseArray;

import com.jockeyjs.JockeyHandler.OnCompletedListener;

public abstract class JockeyImpl implements Jockey {

	// A default Callback that does nothing.
	protected static final JockeyCallback _DEFAULT = new JockeyCallback() {
		@Override
		public void call() {
		}
	};

	private Map<String, CompositeJockeyHandler> _listeners = new HashMap<String, CompositeJockeyHandler>();
	private SparseArray<JockeyCallback> _callbacks = new SparseArray<JockeyCallback>();

	private OnValidateListener _onValidateListener;

	private Handler _handler = new Handler();

	protected WebViewFeature _feature;

	protected JsonConverter<JockeyWebViewPayload> _converter;

	public JockeyImpl() {
	}

	@Override
	public void send(String type) {
		send(type, null);
	}

	@Override
	public void send(String type, Object withPayload) {
		send(type, withPayload, null);
	}

	@Override
	public void send(String type, JockeyCallback complete) {
		send(type, null, complete);

	}

	@Override
	public void on(String type, JockeyHandler... handler) {

		if (!this.handles(type)) {
			_listeners.put(type, new CompositeJockeyHandler());
		}

		_listeners.get(type).add(handler);
	}

	@Override
	public void off(String type) {
		_listeners.remove(type);
	}

	@Override
	public boolean handles(String eventName) {
		return _listeners.containsKey(eventName);
	}

	protected void add(int messageId, JockeyCallback callback) {
		_callbacks.put(messageId, callback);
	}

	public void triggerEventFromWebView(JockeyWebViewPayload envelope) {
		final int messageId = envelope.id;
		String type = envelope.type;

		if (this.handles(type)) {
			JockeyHandler handler = _listeners.get(type);

			handler.perform(envelope.payload, new OnCompletedListener() {
				@Override
				public void onCompleted() {
					// This has to be done with a handler because a webview load
					// must be triggered
					// in the UI thread
					_handler.post(new Runnable() {
						@Override
						public void run() {
							triggerCallbackOnWebView(messageId);
						}
					});
				}
			});
		}
	}

	public void triggerCallbackForMessage(int messageId) {
		try {
			JockeyCallback complete = _callbacks.get(messageId, _DEFAULT);
			complete.call();
		} catch (Exception e) {
			e.printStackTrace();
		}
		_callbacks.remove(messageId);
	}

	public void validate(String host) throws HostValidationException {
		if (_onValidateListener != null && !_onValidateListener.validate(host)) {
			throw new HostValidationException();
		}
	}

	@Override
	public void setOnValidateListener(OnValidateListener listener) {
		_onValidateListener = listener;
	}

	void configure(WebViewFeature feature, JsonConverter converter) {
		_converter = converter;
		_feature = feature;
		_feature.bindJockey(this);
	}

	public static Jockey getDefault() {
		return new DefaultJockeyImpl();
	}

	public void processUri(Uri uri)
		throws HostValidationException {
		String[] parts = uri.getPath().replaceAll("^\\/", "").split("/");
		String host = uri.getHost();

		JockeyWebViewPayload payload = checkPayload(_converter.fromJson(uri.getQuery()));

		if (parts.length > 0) {
			if (host.equals("event")) {
				triggerEventFromWebView(payload);
			} else if (host.equals("callback")) {
				triggerCallbackForMessage(Integer.parseInt(parts[0]));
			}
		}
	}

	private JockeyWebViewPayload checkPayload(JockeyWebViewPayload fromJson)
		throws HostValidationException {
		validateHost(fromJson.host);
		return fromJson;
	}
	private void validateHost(String host) throws HostValidationException {
		validate(host);
	}
}
