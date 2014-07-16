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

package com.arcbees.analytics.shared.options;

public class ApptrackingOptions extends AnalyticsOptions {
    ApptrackingOptions(final OptionsCallback<?> optionsCallback) {
        super(optionsCallback);

    }

    /**
     * Optional.
     * </p>
     * <p>Application identifier.</p>
     * Default Value: None<br>
     * Example Value: <code>com.company.app</code>
     **/
    public ApptrackingOptions ApplicationId(final String ApplicationId) {
        putText("appId", ApplicationId);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Application installer identifier.</p>
     * Default Value: None<br>
     * Example Value: <code>com.platform.vending</code>
     **/
    public ApptrackingOptions ApplicationInstallerId(final String ApplicationInstallerId) {
        putText("appInstallerId", ApplicationInstallerId);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the application name.</p>
     * Default Value: None<br>
     * Example Value: <code>My App</code>
     **/
    public ApptrackingOptions ApplicationName(final String ApplicationName) {
        putText("appName", ApplicationName);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the application version.</p>
     * Default Value: None<br>
     * Example Value: <code>1.2</code>
     **/
    public ApptrackingOptions ApplicationVersion(final String ApplicationVersion) {
        putText("appVersion", ApplicationVersion);
        return this;
    }
}
