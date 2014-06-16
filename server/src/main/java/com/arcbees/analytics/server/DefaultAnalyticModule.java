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

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.inject.Singleton;

public class DefaultAnalyticModule extends AbstractModule {
    private final String trackingCode;
    private final String applicationName;
    private final String applicationVersion;
    private final String clientId;

    public DefaultAnalyticModule(Builder builder) {
        this.trackingCode = builder.trackingCode;
        this.applicationName = builder.applicationName;
        this.applicationVersion = builder.applicationVersion;
        this.clientId = builder.clientId;
    }

    public DefaultAnalyticModule() {
        this(new Builder());
    }

    /**
     * A DefaultAnalyticModule builder.
     */
    public static class Builder {
        private String trackingCode;
        private String applicationName;
        private String applicationVersion;
        private String clientId;

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

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public DefaultAnalyticModule build() {
            return new DefaultAnalyticModule(this);
        }
    }

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    GoogleAnalyticTracker createGoogleAnalytic() {
        GoogleAnalyticTracker googleAnalytic = new GoogleAnalyticTracker(clientId, trackingCode, applicationName,
                applicationVersion);

        return googleAnalytic;
    }
}
