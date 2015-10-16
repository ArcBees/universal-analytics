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

import java.util.Map;
import java.util.logging.Logger;

import com.arcbees.analytics.server.options.ServerOptionsCallback;
import com.arcbees.analytics.server.options.TrackerNameOptionsCallback;
import com.arcbees.analytics.shared.AnalyticsImpl;
import com.arcbees.analytics.shared.AnalyticsPlugin;
import com.arcbees.analytics.shared.GaAccount;
import com.arcbees.analytics.shared.HitType;
import com.arcbees.analytics.shared.options.AnalyticsOptions;
import com.arcbees.analytics.shared.options.CreateOptions;
import com.arcbees.analytics.shared.options.GeneralOptions;
import com.arcbees.analytics.shared.options.TimingOptions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ServerAnalytics extends AnalyticsImpl {
    private static final Logger LOGGER = Logger.getLogger(ServerAnalytics.class.getName());

    private final Provider<ServerOptionsCallback> serverOptionsCallbackProvider;

    @Inject
    ServerAnalytics(
            Provider<ServerOptionsCallback> serverOptionsCallbackProvider,
            @GaAccount String userAccount) {
        super(userAccount);

        this.serverOptionsCallbackProvider = serverOptionsCallbackProvider;
    }

    @Override
    public CreateOptions create(final String userAccount) {
        final Map<String, String> trackerNames = serverOptionsCallbackProvider.get().getTrackerNames();
        return new AnalyticsOptions(new TrackerNameOptionsCallback() {

            @Override
            public void onCallback(String trackerName) {
                trackerNames.put(trackerName, userAccount);
            }
        }).createOptions();
    }

    @Override
    public void enablePlugin(AnalyticsPlugin plugin) {
        // Noop this has no affect on the server
    }

    @Override
    public TimingOptions endTimingEvent(String trackerName, String timingCategory,
            String timingVariableName) {
        Map<String, Long> timingEvents = serverOptionsCallbackProvider.get().getTimingEvents();
        final String key = getTimingKey(timingCategory, timingVariableName);
        if (timingEvents.containsKey(key)) {
            return sendTiming(trackerName, timingCategory, timingVariableName,
                    (int) (System.currentTimeMillis() - timingEvents.remove(key)));
        }

        LOGGER.severe("Timing Event Ended before it was started: " + key);
        return new AnalyticsOptions(new TrackerNameOptionsCallback() {

            @Override
            public void onCallback(String trackerName) {
                // Do nothing a timing event was ended before it was started. This is here just to
                // stop a
                // crash.
            }
        }).timingOptions(timingCategory, timingVariableName, 0);
    }

    @Override
    public AnalyticsOptions send(String trackerName, HitType hitType) {
        final ServerOptionsCallback options = serverOptionsCallbackProvider.get();
        final Map<String, String> trackerNames = options.getTrackerNames();
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
            public void onCallback(String trackerName) {
                // Do nothing, this call is not valid on the server.
            }
        }).generalOptions();
    }

    @Override
    public void startTimingEvent(String timingCategory, String timingVariableName) {
        Map<String,Long> timingEvents = serverOptionsCallbackProvider.get().getTimingEvents();
        timingEvents.put(getTimingKey(timingCategory, timingVariableName),
                System.currentTimeMillis());
    }
}
