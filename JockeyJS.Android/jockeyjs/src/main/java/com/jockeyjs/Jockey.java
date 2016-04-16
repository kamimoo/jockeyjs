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

import com.jockeyjs.webview.WebViewFeature;

import com.jockeyjs.converter.JsonConverter;

/**
 * The primary interface for communicating between a WebView and the local android activity.
 * 
 * @author Paul
 *
 */
public interface Jockey {

	/**
	 * An interface for the app to check the incoming source of an event.
	 * 
	 * @author Paul
	 *
	 */
	interface OnValidateListener {
		boolean validate(String host);
	}

	/**
	 * Attaches one or more handlers to this jockey instance so that is may receive events of the provided type
	 * from a webpage
	 * 
	 * @param type
	 * @param handler
	 */
	void on(String type, JockeyHandler ... handler);
	
	/**
	 * Removes all handlers of the specified name
	 * 
	 * @param type
	 */
	void off(String type);
	
	
	/**
	 * Sends a new event to the webview
	 * 
	 * Equivalent to calling send(type, null, null);
	 * 
	 * @param type
	 */
	void send(String type);
	
	
	/**
	 * Sends a new event to the webview with the included payload
	 * 
	 * Equivalent to calling send(type, payload, null)
	 * 
	 * @param type
	 * @param withPayload
	 */
	void send(String type, Object withPayload);
	
	
	/**
	 * Sends the new event to the webview and registers a callback to listen for the returned value
	 * 
	 * Equivalent to calling send(type, null, complete)
	 * 
	 * @param type
	 * @param complete
	 */
	void send(String type, JockeyCallback complete);
	
	
	/**
	 * Sends the new event to the webview with a payload and a callback to listen for the webpage response.
	 * 
	 * @param type
	 * @param withPayload
	 * @param complete
	 */
	void send(String type, Object withPayload, JockeyCallback complete);
	
	/**
	 * Triggers the callback on the webview with the appropriate messageId
	 * 
	 * @param messageId
	 */
	void triggerCallbackOnWebView(int messageId);
	
	/**
	 * Returns if the Jockey implementation handles the event
	 * 
	 * @param string
	 * @return
	 */
	boolean handles(String string);
	
	
	/**
	 * Sets the listener that will be called when validation needs to be performed
	 * on the incoming host before a redirect
	 * 
	 * @param listener
	 */
	void setOnValidateListener(OnValidateListener listener);

	final class Builder {

		private JsonConverter _converter;
		private WebViewFeature _feature;

		public Jockey build() {
			if (_converter == null) {
				throw new IllegalStateException("Converter required");
			}
			JockeyImpl jockey = new DefaultJockeyImpl();
			jockey.configure(_feature, _converter);
			return jockey;
		}

		public Builder converter(JsonConverter converter) {
			_converter = converter;
			return this;
		}

		public Builder webView(WebViewFeature feature) {
			_feature = feature;
			return this;
		}
	}
}
