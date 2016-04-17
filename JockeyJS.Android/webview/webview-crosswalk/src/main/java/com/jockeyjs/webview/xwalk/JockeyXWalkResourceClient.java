package com.jockeyjs.webview.xwalk;

import android.net.Uri;
import android.util.Log;
import com.jockeyjs.HostValidationException;
import com.jockeyjs.JockeyImpl;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

public class JockeyXWalkResourceClient extends ForwardingXWalkResourceClient {
	private JockeyImpl jockeyImpl;
	private XWalkResourceClient delegate;

	public JockeyXWalkResourceClient(XWalkView view, JockeyImpl jockey) {
		super(view);
		jockeyImpl = jockey;
	}

	public void setDelegate(XWalkResourceClient client) {
		delegate = client;
	}

	@Override
	protected XWalkResourceClient delegate() {
		return delegate;
	}

	@Override
	public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
		try {
			Uri uri = Uri.parse(url);

			if (isJockeyScheme(uri)) {
				jockeyImpl.processUri(uri);
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
}
