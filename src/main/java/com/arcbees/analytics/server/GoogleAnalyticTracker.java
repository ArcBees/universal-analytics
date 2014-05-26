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

import com.arcbees.analytics.client.AnalyticClientIdProvider;
import com.arcbees.analytics.shared.GoogleAnalyticConstants;
import com.google.inject.Inject;

public class GoogleAnalyticTracker {
    private static final String PROTOCOL_VERSION = "1";

    private final String appName;
    private final String appVersion;
    private final String trackingCode;
    private final AnalyticClientIdProvider clientIdProvider;

    @Inject
    public GoogleAnalyticTracker(AnalyticClientIdProvider clientIdProvider,
                                 String trackingCode,
                                 String appName,
                                 String appVersion) {
        this.appName = appName;
        this.appVersion = appVersion;
        this.trackingCode = trackingCode;
        this.clientIdProvider = clientIdProvider;

        trackEvent(GoogleAnalyticConstants.CAT_INITIALIZATION, GoogleAnalyticConstants.APPLICATION_LOADED);
    }

    public boolean trackEvent(String eventCategory, String eventAction) {
        MeasureProtocolRequest measureProtocolRequest = new MeasureProtocolRequest.Builder()
                .protocolVersion(PROTOCOL_VERSION)
                .clientId(clientIdProvider.get())
                .applicationName(appName)
                .applicationVersion(appVersion)
                .trackingCode(trackingCode)
                .hitType(GaParameterConstants.EVENT_HIT_TYPE)
                .eventCategory(eventCategory)
                .eventAction(eventAction)
                .build();

        return measureProtocolRequest.executeRequest();
    }

    public boolean trackEvent(String eventCategory, String eventAction, String eventLabel) {
        MeasureProtocolRequest measureProtocolRequest = new MeasureProtocolRequest.Builder()
                .protocolVersion(PROTOCOL_VERSION)
                .clientId(clientIdProvider.get())
                .applicationName(appName)
                .applicationVersion(appVersion)
                .trackingCode(trackingCode)
                .hitType(GaParameterConstants.EVENT_HIT_TYPE)
                .eventCategory(eventCategory)
                .eventAction(eventAction)
                .eventLabel(eventLabel)
                .build();

        return measureProtocolRequest.executeRequest();
    }
}
