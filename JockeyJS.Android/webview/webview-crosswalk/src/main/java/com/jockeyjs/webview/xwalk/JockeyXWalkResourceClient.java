package com.jockeyjs.webview.xwalk;

import android.net.Uri;
import android.util.Log;
import com.jockeyjs.HostValidationException;
import com.jockeyjs.JockeyImpl;
import com.jockeyjs.JockeyWebViewPayload;
import com.jockeyjs.converter.JsonConverter;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

public class JockeyXWalkResourceClient extends XWalkResourceClient {
	private JockeyImpl jockeyImpl;
	private JsonConverter<JockeyWebViewPayload> converter;

	public JockeyXWalkResourceClient(XWalkView view, JockeyImpl jockey) {
		super(view);
		jockeyImpl = jockey;
	}

	public void setConverter(JsonConverter<JockeyWebViewPayload> converter) {
		this.converter = converter;
	}

	@Override
	public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
		try {
			Uri uri = Uri.parse(url);

			if (isJockeyScheme(uri)) {
				processUri(view, uri);
				return true;
			}
		} catch (HostValidationException e) {
			e.printStackTrace();
			Log.e("Jockey", "The source of the event could not be validated!");
		}
		return false;
	}

	public boolean isJockeyScheme(Uri uri) {
		return uri.getScheme().equals("jockey") && !uri.getQuery().equals("");
	}

	public void processUri(XWalkView view, Uri uri)
		throws HostValidationException {
		String[] parts = uri.getPath().replaceAll("^\\/", "").split("/");
		String host = uri.getHost();

		JockeyWebViewPayload payload = checkPayload(converter.fromJson(uri.getQuery()));

		if (parts.length > 0) {
			if (host.equals("event")) {
				jockeyImpl.triggerEventFromWebView(payload);
			} else if (host.equals("callback")) {
				jockeyImpl.triggerCallbackForMessage(Integer.parseInt(parts[0]));
			}
		}
	}

	public JockeyWebViewPayload checkPayload(JockeyWebViewPayload fromJson)
		throws HostValidationException {
		validateHost(fromJson.host);
		return fromJson;
	}

	private void validateHost(String host) throws HostValidationException {
		jockeyImpl.validate(host);
	}
}
