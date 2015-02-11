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
    ApptrackingOptions(OptionsCallback<?> optionsCallback) {
        super(optionsCallback);
    }

    /**
     * Optional. </p>
     * <p>
     * Application identifier.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>com.company.app</code>
     **/
    public ApptrackingOptions applicationId(String applicationId) {
        putText("appId", applicationId);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     * Application installer identifier.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>com.platform.vending</code>
     **/
    public ApptrackingOptions applicationInstallerId(String applicationInstallerId) {
        putText("appInstallerId", applicationInstallerId);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     * Specifies the application name.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>My App</code>
     **/
    public ApptrackingOptions applicationName(String applicationName) {
        putText("appName", applicationName);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     * Specifies the application version.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>1.2</code>
     **/
    public ApptrackingOptions applicationVersion(String applicationVersion) {
        putText("appVersion", applicationVersion);
        return this;
    }
}
