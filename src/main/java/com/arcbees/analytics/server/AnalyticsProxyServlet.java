package com.arcbees.analytics.server;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arcbees.analytics.server.options.ServerOptionsCallback;

@Singleton
public class AnalyticsProxyServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private ServerOptionsCallbackProvider serverOptionsCallbackProvier;
	
	@Inject
	public AnalyticsProxyServlet(ServerOptionsCallbackProvider serverOptionsCallbackProvider) {
		this.serverOptionsCallbackProvier = serverOptionsCallbackProvider;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServerOptionsCallback serverOptionsCallback = serverOptionsCallbackProvier.get();
		String qString = serverOptionsCallback.getOptions() + "&" + req.getQueryString();
		serverOptionsCallback.onCallback(qString);
	}
	
	

}
