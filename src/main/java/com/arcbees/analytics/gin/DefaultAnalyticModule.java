/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.analytics.gin;

import javax.inject.Singleton;

import com.arcbees.analytics.annotation.GaAccount;
import com.arcbees.analytics.core.AnalyticClientIdProvider;
import com.arcbees.analytics.core.GoogleAnalytics;
import com.arcbees.analytics.core.GoogleAnalyticsImpl;
import com.arcbees.analytics.service.GoogleAnalyticTracker;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class DefaultAnalyticModule extends AbstractModule {
    private final String trackingCode;
    private final String applicationName;
    private final String applicationVersion;
    private final AnalyticClientIdProvider clientIdProvider;

    public DefaultAnalyticModule(String trackingCode,
                                 String applicationName,
                                 String applicationVersion,
                                 AnalyticClientIdProvider clientIdProvider) {
        this.trackingCode = trackingCode;
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
        this.clientIdProvider = clientIdProvider;
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
