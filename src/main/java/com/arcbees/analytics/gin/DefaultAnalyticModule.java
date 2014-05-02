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

package com.arcbees.analytics.gin;

import javax.inject.Singleton;

import com.arcbees.analytics.annotation.GaAccount;
import com.arcbees.analytics.client.AnalyticClientIdProvider;
import com.arcbees.analytics.client.GoogleAnalytics;
import com.arcbees.analytics.client.GoogleAnalyticsImpl;
import com.arcbees.analytics.server.GoogleAnalyticTracker;
import com.arcbees.analytics.shared.GoogleAnalyticConstants;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class DefaultAnalyticModule extends AbstractModule {
    private final String trackingCode;
    private final String applicationName;
    private final String applicationVersion;
    private final AnalyticClientIdProvider clientIdProvider;

    public DefaultAnalyticModule(Builder builder) {
        this.trackingCode = builder.trackingCode;
        this.applicationName = builder.applicationName;
        this.applicationVersion = builder.applicationVersion;
        this.clientIdProvider = builder.clientIdProvider;
    }

    /**
     * A DefaultAnalyticModule builder.
     */
    public static class Builder {

        private String trackingCode;
        private String applicationName;
        private String applicationVersion;
        private AnalyticClientIdProvider clientIdProvider;

        public Builder() {
        }

        public Builder trackingCode(String trackingCode) {
            this.trackingCode = trackingCode;
            return this;
        }

        public Builder applicationName(String applicationName) {
            this.applicationName = applicationName;
            return this;
        }

        public Builder applicationVersion(String applicationVersion) {
            this.applicationVersion = applicationVersion;
            return this;
        }

        public Builder analyticClientIdProvider(AnalyticClientIdProvider clientIdProvider) {
            this.clientIdProvider = clientIdProvider;
            return this;
        }

        public DefaultAnalyticModule build() {
            return new DefaultAnalyticModule(this);
        }
    }

    @Override
    protected void configure() {
        bindConstant().annotatedWith(GaAccount.class).to(trackingCode);
        bind(GoogleAnalytics.class).to(GoogleAnalyticsImpl.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    GoogleAnalyticTracker createGoogleAnalytic() {
        GoogleAnalyticTracker googleAnalytic
                = GoogleAnalyticTracker.build(clientIdProvider.get(), trackingCode, applicationName, applicationVersion);

        googleAnalytic.trackEvent(GoogleAnalyticConstants.CAT_INITIALIZATION,
                GoogleAnalyticConstants.APPLICATION_LOADED);

        return googleAnalytic;
    }
}
