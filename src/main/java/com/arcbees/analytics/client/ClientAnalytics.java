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

import com.arcbees.analytics.shared.AnalyticsImpl;
import com.arcbees.analytics.shared.AnalyticsPlugin;
import com.arcbees.analytics.shared.GaAccount;
import com.arcbees.analytics.shared.HitType;
import com.arcbees.analytics.shared.options.AnalyticsOptions;
import com.arcbees.analytics.shared.options.CreateOptions;
import com.arcbees.analytics.shared.options.GeneralOptions;
import com.arcbees.analytics.shared.options.TimingOptions;
import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;

public class ClientAnalytics extends AnalyticsImpl {
    private final Map<String, Double> timingEvents = new HashMap<>();

    @Inject
    ClientAnalytics(@GaAccount final String userAccount,
            @AutoCreate final boolean autoCreate, 
            @TrackInitialPageView final boolean trackInitialPageView) {
        super(userAccount);

        init();

        if (autoCreate) {
            create().go();
            if (trackInitialPageView) {
            	sendPageView().go();
            }
        }
    }

    private void call(final JSONValue... params) {
        final JSONArray aryParams = new JSONArray();
        for (final JSONValue p : params) {
            aryParams.set(aryParams.size(), p);
        }
        nativeCall(aryParams.getJavaScriptObject());
    }

    @Override
    public CreateOptions create(final String userAccount) {
        return new AnalyticsOptions(new JSONOptionsCallback() {

            @Override
            public void onCallback(final JSONObject options) {
                call(new JSONString("create"), new JSONString(userAccount), options);
                setGlobalSettings().forceSsl(true).go();
            }
        }).createOptions();
    }

    @Override
    public void enablePlugin(final AnalyticsPlugin plugin) {
        if (plugin.getJsName() != null) {
            call(new JSONString("require"), new JSONString(plugin.getFieldName()), new JSONString(plugin.getJsName()));
        } else {
            call(new JSONString("require"), new JSONString(plugin.getFieldName()));
        }
    }

    @Override
    public TimingOptions endTimingEvent(final String trackerName, final String timingCategory,
            final String timingVariableName) {
        final String key = getTimingKey(timingCategory, timingVariableName);
        if (timingEvents.containsKey(key)) {
            return sendTiming(trackerName, timingCategory, timingVariableName,
                    (int) (Duration.currentTimeMillis() - timingEvents.remove(key)));
        }
        return new AnalyticsOptions(new JSONOptionsCallback() {

            @Override
            public void onCallback(final JSONObject options) {
                //Do nothing a timing event was ended before it was started.  This is here just to stop a crash.
            }
        }).timingOptions(timingCategory, timingVariableName, 0);
    }

    private native void init()/*-{
        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        })($wnd,$doc,'script','//www.google-analytics.com/analytics.js','__ua');
    }-*/;

    private native void nativeCall(final JavaScriptObject params) /*-{
        $wnd.__ua.apply(null, params);
    }-*/;

    @Override
    public AnalyticsOptions send(final String trackerName, final HitType hitType) {
        return new AnalyticsOptions(new JSONOptionsCallback() {

            @Override
            public void onCallback(final JSONObject options) {
                call(new JSONString(trackerName == null ? "send" : trackerName + ".send"),
                        new JSONString(hitType.getFieldName()), options);
            }
        });
    }

    @Override
    public GeneralOptions setGlobalSettings() {
        return new AnalyticsOptions(new JSONOptionsCallback() {

            @Override
            public void onCallback(final JSONObject options) {
                call(new JSONString("set"), options);
            }

        }).generalOptions();
    }

    @Override
    public void startTimingEvent(final String timingCategory, final String timingVariableName) {
        timingEvents.put(getTimingKey(timingCategory, timingVariableName), Duration.currentTimeMillis());
    }
}
