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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.arcbees.analytics.server.options.ServerOptionsCallback;
import com.arcbees.analytics.server.options.TrackerNameOptionsCallback;
import com.arcbees.analytics.shared.AnalyticsImpl;
import com.arcbees.analytics.shared.AnalyticsPlugin;
import com.arcbees.analytics.shared.HitType;
import com.arcbees.analytics.shared.options.AnalyticsOptions;
import com.arcbees.analytics.shared.options.CreateOptions;
import com.arcbees.analytics.shared.options.GeneralOptions;
import com.arcbees.analytics.shared.options.TimingOptions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class ServerAnalytics extends AnalyticsImpl {

    private final Provider<ServerOptionsCallback> serverOptionsCallbackProvider;

    private final Map<String, Long> timingEvents = new HashMap<>();

    private final Map<String, String> trackerNames = new HashMap<>();

    private final static Logger logger = Logger.getLogger(ServerAnalytics.class.getName());

    @Inject
    ServerAnalytics(final Provider<ServerOptionsCallback> serverOptionsCallbackProvider,
            @Named("gaAccount") final String userAccount) {
        super(userAccount);
        this.serverOptionsCallbackProvider = serverOptionsCallbackProvider;

    }


    @Override
    public CreateOptions create(final String userAccount) {
        return new AnalyticsOptions(new TrackerNameOptionsCallback() {

            @Override
            public void onCallback(final String trackerName) {
                trackerNames.put(trackerName, userAccount);

            }
        }).createOptions();
    }

    @Override
    public void enablePlugin(final AnalyticsPlugin plugin) {
        // Noop this has no affect on the server

    }


    @Override
    public TimingOptions endTimingEvent(final String trackerName, final String timingCategory,
            final String timingVariableName) {
        final String key = getTimingKey(timingCategory, timingVariableName);
        if (timingEvents.containsKey(key)) {
            return sendTiming(trackerName, timingCategory, timingVariableName,
                    (int) (System.currentTimeMillis() - timingEvents.remove(key)));
        }
        logger.severe("Timing Event Ended before it was started: " + key);
        return new AnalyticsOptions(new TrackerNameOptionsCallback() {

            @Override
            public void onCallback(final String trackerName) {
                //Do nothing a timing event was ended before it was started.  This is here just to stop a crash.
            }
        }).timingOptions(timingCategory, timingVariableName, 0);
    }
    @Override
    public AnalyticsOptions send(final String trackerName, final HitType hitType) {
        final ServerOptionsCallback options = serverOptionsCallbackProvider.get();
        if (trackerName != null) {
            options.putText("tid", trackerNames.get(trackerName));
        }
        options.putText("hitType", hitType.getFieldName());
        return new AnalyticsOptions(options);
    }

    @Override
    public GeneralOptions setGlobalSettings() {
        return new AnalyticsOptions(new TrackerNameOptionsCallback() {

            @Override
            public void onCallback(final String trackerName) {
                //Do nothing, this call is not valid on the server.
            }
        }).generalOptions();
    }

    @Override
    public void startTimingEvent(final String timingCategory, final String timingVariableName) {
        timingEvents.put(timingCategory + ":" + timingVariableName, System.currentTimeMillis());

    }

}
