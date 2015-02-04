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

import com.arcbees.analytics.shared.Analytics;
import com.google.gwt.inject.client.AbstractGinModule;

public class AnalyticsModule extends AbstractGinModule {
    public static class Builder {
        private final String userAccount;
        private boolean autoCreate = true;
        private boolean trackUncaughtExceptions = false;
        private boolean trackInitialPageView = true;
        private String fallbackPath = "";

        public Builder(final String userAccount) {
            this.userAccount = userAccount;
        }

        /**
         * Set this to false if you want to create the universal analytics tracker with custom options.
         * You can manually create the tracker by calling
         * <pre> {@code 
         * analytics.create().go()
         * analytics.sendPageView().go();
         * } </pre>
         * from your bootstrapper or entrypoint.
         * 
         * @param autoCreate
         * @return Builder
         */
        public Builder autoCreate(final boolean autoCreate) {
            this.autoCreate = autoCreate;
            return this;
        }
        
        /**
         * By default the initial page view will be tracked.
         * @param trackInitialPageView
         * @return Builder
         */
        public Builder trackInitialPageView(final boolean trackInitialPageView) {
        	this.trackInitialPageView = trackInitialPageView;
        	return this;
        }
        
        /**
         * Analytics needs to load the script from www.google-analytics.com/analytics.js.<p>
         * 
         * This call can fail relatively often eg: because the user is in a country that blocks google,
         * or if the user is running an extension that blocks third party scripts.<p>
         * 
         * Set the fallbackPath to automatically proxy the analytics calls via your own server.<p>
         * 
         * For this to work you must set up a proxy on your server using:
         * {@link com.arcbees.analytics.server.AnalyticsProxyModule}
         * @param fallbackPath
         * @return Builder
         */
        public Builder setFallbackPath(final String fallbackPath) {
            this.fallbackPath = fallbackPath;
            return this;
        }
        
        public AnalyticsModule build() {
            return new AnalyticsModule(userAccount, autoCreate, trackUncaughtExceptions, trackInitialPageView, fallbackPath);
        }

        /**
         * Set this to true if you want uncaught exceptions to be tracked.
         * @param trackUncaughtExceptions
         * @return Builder
         */
        public Builder trackUncaughtExceptions(final boolean trackUncaughtExceptions) {
            this.trackUncaughtExceptions = trackUncaughtExceptions;
            return this;
        }
    }

    private final String userAccount;
    private final boolean autoCreate;
    private final boolean trackUncaughtExceptions;
	private final boolean trackInitialPageView;
	private final String fallbackPath;

    private AnalyticsModule(final String userAccount,
            final boolean autoCreate,
            final boolean trackUncaughtExceptions,
            final boolean trackInitialPageView, final String fallbackPath) {
        this.userAccount = userAccount;
        this.autoCreate = autoCreate;
        this.trackUncaughtExceptions = trackUncaughtExceptions;
        this.trackInitialPageView = trackInitialPageView;
        this.fallbackPath = fallbackPath;
    }

    @Override
    protected void configure() {
        bindConstant().annotatedWith(GaAccount.class).to(userAccount);
        bindConstant().annotatedWith(AutoCreate.class).to(autoCreate);
        bindConstant().annotatedWith(TrackInitialPageView.class).to(trackInitialPageView);
        bindConstant().annotatedWith(FallbackPath.class).to(fallbackPath);
        bind(ClientAnalytics.class).asEagerSingleton();
        bind(Analytics.class).to(ClientAnalytics.class);
        if (trackUncaughtExceptions) {
            bind(UncaughtExceptionTracker.class).asEagerSingleton();
        }
    }
}
