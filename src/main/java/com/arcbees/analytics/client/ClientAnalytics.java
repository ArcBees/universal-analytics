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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.arcbees.analytics.shared.AnalyticsImpl;
import com.arcbees.analytics.shared.AnalyticsPlugin;
import com.arcbees.analytics.shared.GaAccount;
import com.arcbees.analytics.shared.HitType;
import com.arcbees.analytics.shared.ProtocolTranslator;
import com.arcbees.analytics.shared.options.AnalyticsOptions;
import com.arcbees.analytics.shared.options.CreateOptions;
import com.arcbees.analytics.shared.options.GeneralOptions;
import com.arcbees.analytics.shared.options.TimingOptions;
import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;

public class ClientAnalytics extends AnalyticsImpl {
    private static final Logger LOGGER = Logger.getLogger(ClientAnalytics.class.getName());
    private final Map<String, Double> timingEvents = new HashMap<>();
    private final String fallbackPath;

    private final Timer fallbackInitTimer = new Timer() {
        @Override
        public void run() {
            if (loadFallback()) {
                LOGGER.info("Universal analytics not loaded after 10 seconds, using fallback");
            }
        }
    };

    @Inject
    ClientAnalytics(
            @GaAccount String userAccount,
            @AutoCreate boolean autoCreate,
            @AutoInject boolean autoInject,
            @TrackInitialPageView boolean trackInitialPageView,
            @FallbackPath String fallbackPath) {
        super(userAccount);

        this.fallbackPath = fallbackPath;

        if (autoInject) {
            init();
        }

        if (autoCreate) {
            create().go();
            if (trackInitialPageView) {
                sendPageView().go();
            }
        }
    }

    private void call(JSONValue... params) {
        final JSONArray aryParams = new JSONArray();
        for (JSONValue p : params) {
            aryParams.set(aryParams.size(), p);
        }
        nativeCall(aryParams.getJavaScriptObject());
    }

    @Override
    public CreateOptions create(final String userAccount) {
        return new AnalyticsOptions(new JsonOptionsCallback() {

            @Override
            public void onCallback(JSONObject options) {
                call(new JSONString("create"), new JSONString(userAccount), options);
                setGlobalSettings().forceSsl(true).go();
            }
        }).createOptions();
    }

    @Override
    public void enablePlugin(AnalyticsPlugin plugin) {
        if (plugin.getJsName() != null) {
            call(new JSONString("require"), new JSONString(plugin.getFieldName()), new JSONString(
                    plugin.getJsName()));
        } else {
            call(new JSONString("require"), new JSONString(plugin.getFieldName()));
        }
    }

    @Override
    public TimingOptions endTimingEvent(String trackerName, String timingCategory,
            String timingVariableName) {
        final String key = getTimingKey(timingCategory, timingVariableName);
        if (timingEvents.containsKey(key)) {
            return sendTiming(trackerName, timingCategory, timingVariableName,
                    (int) (Duration.currentTimeMillis() - timingEvents.remove(key)));
        }
        return new AnalyticsOptions(new JsonOptionsCallback() {

            @Override
            public void onCallback(JSONObject options) {
                // Do nothing a timing event was ended before it was started.
                // This is here just to stop a crash.
            }
        }).timingOptions(timingCategory, timingVariableName, 0);
    }

    private void init() {
        nativeInit();
        if (!fallbackPath.isEmpty()) {
            fallbackInitTimer.schedule(10000);
        }
    }

    public void fallback(JsArrayMixed arguments) {
        if ("send".equals(arguments.getString(0))) {
            JSONObject jsonOptions = new JSONObject(arguments.getObject(arguments.length() - 1));
            StringBuilder url = new StringBuilder();
            url.append(fallbackPath).append("?");
            url.append(ProtocolTranslator.getFieldName("hitType")).append("=")
                    .append(URL.encodeQueryString(arguments.getString(1)));

            for (String key : jsonOptions.keySet()) {
                if (!"hitCallback".equals(key)) {
                    JSONValue jsonValue = jsonOptions.get(key);
                    String strValue = "";
                    if (jsonValue.isBoolean() != null) {
                        strValue = jsonValue.isBoolean().booleanValue() + "";
                    } else if (jsonValue.isNumber() != null) {
                        strValue = jsonValue.isNumber().doubleValue() + "";
                    } else if (jsonValue.isString() != null) {
                        strValue = jsonValue.isString().stringValue();
                    }
                    url.append("&").append(ProtocolTranslator.getFieldName(key)).append("=")
                            .append(URL.encodeQueryString(strValue));
                }
            }
            try {
                RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET,
                        url.toString());
                requestBuilder.setCallback(new RequestCallback() {

                    @Override
                    public void onResponseReceived(Request request, Response response) {
                        // TODO call hitcallback if needed.
                    }

                    @Override
                    public void onError(Request request, Throwable exception) {
                        // TODO Auto-generated method stub
                    }
                });
                requestBuilder.send();
            } catch (RequestException e) {
            }
        }
    }

    private native boolean loadFallback() /*-{
        var name = $wnd['GoogleAnalyticsObject'];
        var preCalled = $wnd[name].q;
        if (preCalled !== undefined) {
            var that = this;
            $wnd[name] = function() {
                // CHECKSTYLE_OFF
                $entry(that.@com.arcbees.analytics.client.ClientAnalytics::fallback(Lcom/google/gwt/core/client/JsArrayMixed;)(arguments));
                // CHECKSTYLE_ON
            };
            for (var i = 0; i < preCalled.length; i++) {
                $wnd[name].apply(null, preCalled[i]);
            }
            return true;
        }
        return false;
    }-*/;

    private native void nativeInit() /*-{
        (function(i, s, o, g, r, a, m) {
            i['GoogleAnalyticsObject'] = r;
            i[r] = i[r] || function() {
                (i[r].q = i[r].q || []).push(arguments)
            }, i[r].l = 1 * new Date();
            a = s.createElement(o), m = s.getElementsByTagName(o)[0];
            a.async = 1;
            a.src = g;
            m.parentNode.insertBefore(a, m)
        })($wnd, $doc, 'script', 'https://www.google-analytics.com/analytics.js',
                '__ua');
    }-*/;

    private native void nativeCall(JavaScriptObject params) /*-{
        $wnd.__ua.apply(null, params);
    }-*/;

    @Override
    public AnalyticsOptions send(final String trackerName, final HitType hitType) {
        return new AnalyticsOptions(new JsonOptionsCallback() {

            @Override
            public void onCallback(JSONObject options) {
                call(new JSONString(trackerName == null ? "send" : trackerName + ".send"),
                        new JSONString(hitType.getFieldName()), options);
            }
        });
    }

    @Override
    public GeneralOptions setGlobalSettings() {
        return new AnalyticsOptions(new JsonOptionsCallback() {

            @Override
            public void onCallback(JSONObject options) {
                call(new JSONString("set"), options);
            }

        }).generalOptions();
    }

    @Override
    public void startTimingEvent(String timingCategory, String timingVariableName) {
        timingEvents.put(getTimingKey(timingCategory, timingVariableName),
                Duration.currentTimeMillis());
    }
}
