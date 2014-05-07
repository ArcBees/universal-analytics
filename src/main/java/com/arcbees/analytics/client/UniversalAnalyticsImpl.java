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

package com.arcbees.analytics.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;

/**
 * Default {@link UniversalAnalytics} implementation that uses JSNI to
 * expose Universal Analytics javascript methods.
 */
public class UniversalAnalyticsImpl implements UniversalAnalytics {
    @Override
    public void init(String userAccount) {
        Element firstScript = Document.get().getElementsByTagName("script").getItem(
                0);

        ScriptElement config = Document.get().createScriptElement(
                "var _gaq = _gaq || []; _gaq.push(arguments); _gaq.l = (new Date()).getTime();" +
                        "_gaq.q = []; _gaq('create' '" + userAccount + "', 'auto')");

        firstScript.getParentNode().insertBefore(config, firstScript);

        ScriptElement script = Document.get().createScriptElement();

        // Add the universal analytics script.
        script.setSrc("//google-analytics.com/analytics.js");
        script.setType("text/javascript");
        script.setAttribute("async", "true");

        firstScript.getParentNode().insertBefore(script, firstScript);
    }

    @Override
    public native void sslTracking(boolean enableSSL) /*-{
        $wnd._gaq('set', 'forceSSL', enableSSL);
    }-*/;

    @Override
    public native void addAccount(String trackerName, String userAccount) /*-{
        $wnd._gaq('create','" + userAccount + "', 'auto', {'name': '" + trackerName + "'});
    }-*/;

    @Override
    public native void trackPageview() /*-{
        $wnd._gaq('send', 'pageview');
    }-*/;

    public native void trackPageview(String pageName) /*-{
        if (!pageName.match("^/") == "/") {
            pageName = "/" + pageName;
        }

        $wnd._gaq('send', 'pageview', pageName);
    }-*/;

    @Override
    public native void trackPageview(String trackerName, String pageName) /*-{
        if (!pageName.match("^/") == "/") {
            pageName = "/" + pageName;
        }

        $wnd._gaq('"+ trackerName + ".send"', 'pageview', pageName);
    }-*/;

    @Override
    public native void trackEvent(String category, String action) /*-{
        $wnd._gaq('send', 'event', category, action);
    }-*/;

    @Override
    public native void trackEvent(String category, String action, String optLabel) /*-{
        $wnd._gaq('send', 'event', category, action, optLabel);
    }-*/;

    @Override
    public native void trackEvent(String category, String action,
            String optLabel, int optValue) /*-{
        $wnd._gaq('send', 'event', category, action, optLabel, optValue);
    }-*/;

    @Override
    public native void trackEvent(String category, String action, String optLabel, int optValue,
            int optNonInteraction) /*-{
        $wnd._gaq('send', 'event', category, action, optLabel, optValue, {'nonInteraction:':"+ optNonInteraction + "});
    }-*/;
}
