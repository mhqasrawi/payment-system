package com.progressoft.jip.ui.webrendering;

public class HtmlRendererException extends RuntimeException {
	public HtmlRendererException() {
		super();
	}

	public HtmlRendererException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public HtmlRendererException(String message, Throwable cause) {
		super(message, cause);
	}

	public HtmlRendererException(String message) {
		super(message);
	}

	public HtmlRendererException(Throwable cause) {
		super(cause);
	}
}
