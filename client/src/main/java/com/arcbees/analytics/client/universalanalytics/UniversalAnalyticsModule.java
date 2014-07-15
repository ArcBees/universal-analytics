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
package com.arcbees.analytics.client.universalanalytics;

import com.arcbees.analytics.client.GaAccount;
import com.arcbees.analytics.client.GoogleAnalytics;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.name.Names;

public class UniversalAnalyticsModule extends AbstractGinModule {
    public static class Builder {
        private final String userAccount;
        private boolean autoCreate = true;

        public Builder(final String userAccount) {
            this.userAccount = userAccount;
        }

        /**
         * Set this to false if you want to create the universal analytics tracker with custom options.
         * You can manually create the tracker by calling analytics.create() from your bootstrapper;
         * @param autoCreate
         * @return
         */
        public Builder autoCreate(final boolean autoCreate) {
            this.autoCreate = autoCreate;
            return this;
        }

        public UniversalAnalyticsModule build() {
            return new UniversalAnalyticsModule(userAccount, autoCreate);
        }
    }

    private final String userAccount;
    private boolean autoCreate;

    private UniversalAnalyticsModule(final String userAccount,
            final boolean autoCreate) {
        this.userAccount = userAccount;
        this.autoCreate = autoCreate;
    }

    @Override
    protected void configure() {
        bindConstant().annotatedWith(GaAccount.class).to(userAccount);
        bindConstant().annotatedWith(Names.named("uaAutoCreate")).to(autoCreate);
        bind(UniversalAnalyticsImpl.class).asEagerSingleton();
        bind(GoogleAnalytics.class).to(UniversalAnalyticsImpl.class);
        bind(UniversalAnalytics.class).to(UniversalAnalyticsImpl.class);
    }
}
