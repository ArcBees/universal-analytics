/**
 * Copyright 2014 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.arcbees.analytics.server;

import java.io.IOException;
import java.util.Random;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arcbees.analytics.server.options.ServerOptionsCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class ServerOptionsCallbackProvider implements Filter, Provider<ServerOptionsCallback> {
    private final static int ANALYTICS_VERSION = 1;
    private final String userAccount;
    private String cookieValue;
    private final Random random = new Random();

    @Inject
    ServerOptionsCallbackProvider(@Named("gaAccount") final String userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public void destroy() {
        //nothing to destoy
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
            doHttpFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void doHttpFilter(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain) throws IOException, ServletException {
        cookieValue = null;
        final Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (final Cookie cookie : cookies) {
                if (cookie.getName().equals("_ga")) {
                    cookieValue = cookie.getValue();
                    if (cookieValue.split("\\.").length != 4) {
                        cookieValue = null;
                    }
                }
            }
        }
        final boolean createCookie = cookieValue == null;
        if (createCookie) {
            final int hostLevel = request.getServerName().split("\\.").length;
            cookieValue = "GA" + ANALYTICS_VERSION + "." + hostLevel + "." + random.nextInt(Integer.MAX_VALUE) + "."
                    + (System.currentTimeMillis() / 1000);
            final Cookie cookie = new Cookie("_ga", cookieValue);
            cookie.setPath("/");
            cookie.setDomain("." + request.getServerName());
            cookie.setMaxAge(63072000);
            response.addCookie(cookie);
        }

        chain.doFilter(request, response);
    }

    @Override
    public ServerOptionsCallback get() {
        final ServerOptionsCallback result = new ServerOptionsCallback();
        result.putText("v", ANALYTICS_VERSION + "");
        result.putText("tid", userAccount);
        final String[] split = cookieValue.split("\\.");
        result.putText("cid", split[2] + "." + split[3]);
        return result;
    }

    @Override
    public void init(final FilterConfig config) throws ServletException {
        //nothing to init
    }
}
