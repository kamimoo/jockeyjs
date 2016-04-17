package com.jockeyjs.webview.xwalk;

import android.net.http.SslError;
import android.webkit.ValueCallback;
import java.io.InputStream;
import java.util.Map;
import org.xwalk.core.ClientCertRequest;
import org.xwalk.core.XWalkHttpAuthHandler;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.XWalkWebResourceRequest;
import org.xwalk.core.XWalkWebResourceResponse;

public abstract class ForwardingXWalkResourceClient extends XWalkResourceClient {

	protected abstract XWalkResourceClient delegate();

	protected boolean hasDelegate() {
		return delegate() != null;
	}

	public ForwardingXWalkResourceClient(XWalkView view) {
		super(view);
	}

	@Override
	public XWalkWebResourceResponse createXWalkWebResourceResponse(String mimeType, String encoding,
		InputStream data) {
		if (hasDelegate()) {
			return delegate().createXWalkWebResourceResponse(mimeType, encoding, data);
		} else {
			return super.createXWalkWebResourceResponse(mimeType, encoding, data);
		}
	}

	@Override
	public XWalkWebResourceResponse createXWalkWebResourceResponse(String mimeType, String encoding,
		InputStream data, int statusCode, String reasonPhrase, Map<String, String> responseHeaders) {
		if (hasDelegate()) {
			return delegate().createXWalkWebResourceResponse(mimeType, encoding, data, statusCode,
				reasonPhrase, responseHeaders);
		} else {
			return super.createXWalkWebResourceResponse(mimeType, encoding, data, statusCode,
				reasonPhrase, responseHeaders);
		}
	}

	@Override
	public void doUpdateVisitedHistory(XWalkView view, String url, boolean isReload) {
		if (hasDelegate()) {
			delegate().doUpdateVisitedHistory(view, url, isReload);
		} else {
			super.doUpdateVisitedHistory(view, url, isReload);
		}
	}

	@Override
	public void onDocumentLoadedInFrame(XWalkView view, long frameId) {
		if (hasDelegate()) {
			delegate().onDocumentLoadedInFrame(view, frameId);
		} else {
			super.onDocumentLoadedInFrame(view, frameId);
		}
	}

	@Override
	public void onLoadFinished(XWalkView view, String url) {
		if (hasDelegate()) {
			delegate().onLoadFinished(view, url);
		} else {
			super.onLoadFinished(view, url);
		}
	}

	@Override
	public void onLoadStarted(XWalkView view, String url) {
		if (hasDelegate()) {
			delegate().onLoadStarted(view, url);
		} else {
			super.onLoadStarted(view, url);
		}
	}

	@Override
	public void onProgressChanged(XWalkView view, int progressInPercent) {
		if (hasDelegate()) {
			delegate().onProgressChanged(view, progressInPercent);
		} else {
			super.onProgressChanged(view, progressInPercent);
		}
	}

	@Override
	public void onReceivedClientCertRequest(XWalkView view, ClientCertRequest handler) {
		if (hasDelegate()) {
			delegate().onReceivedClientCertRequest(view, handler);
		} else {
			super.onReceivedClientCertRequest(view, handler);
		}
	}

	@Override
	public void onReceivedHttpAuthRequest(XWalkView view, XWalkHttpAuthHandler handler, String host,
		String realm) {
		if (hasDelegate()) {
			delegate().onReceivedHttpAuthRequest(view, handler, host, realm);
		} else {
			super.onReceivedHttpAuthRequest(view, handler, host, realm);
		}
	}

	@Override
	public void onReceivedLoadError(XWalkView view, int errorCode, String description,
		String failingUrl) {
		if (hasDelegate()) {
			delegate().onReceivedLoadError(view, errorCode, description, failingUrl);
		} else {
			super.onReceivedLoadError(view, errorCode, description, failingUrl);
		}
	}

	@Override
	public void onReceivedSslError(XWalkView view, ValueCallback<Boolean> callback, SslError error) {
		if (hasDelegate()) {
			delegate().onReceivedSslError(view, callback, error);
		} else {
			super.onReceivedSslError(view, callback, error);
		}
	}

	@Override
	public XWalkWebResourceResponse shouldInterceptLoadRequest(XWalkView view,
		XWalkWebResourceRequest request) {
		if (hasDelegate()) {
			return delegate().shouldInterceptLoadRequest(view, request);
		} else {
			return super.shouldInterceptLoadRequest(view, request);
		}
	}
}
