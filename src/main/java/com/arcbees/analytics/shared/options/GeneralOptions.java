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

import com.arcbees.analytics.shared.Task;

public class GeneralOptions extends AnalyticsOptions {
    GeneralOptions(OptionsCallback<?> optionsCallback) {
        super(optionsCallback);
    }

    /**
     * Optional. </p>
     * <p>
     * When present, the IP address of the sender will be anonymized.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>true</code>
     **/
    public GeneralOptions anonymizeIp(boolean anonymizeIp) {
        putBoolean("anonymizeIp", anonymizeIp);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     * By default, tracking beacons sent from https pages will be sent using https while beacons
     * sent from http pages will be sent using http. Setting forceSSL to true will force http pages
     * to also send all beacons using https.
     * </p>
     * Default Value: true<br>
     * Example Value: <code>true</code>
     **/
    public GeneralOptions forceSsl(boolean forceSsl) {
        putBoolean("forceSSL", forceSsl);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     *  Setting this to true, will instruct the client to use navigator.sendBeacon to send the hit.
     *  This is useful in cases where you wish to track an event just before a user navigates away
     *  from your site, without delaying the navigation.
     *  If the browser does not support navigator.sendBeacon, the hit will be sent normally.
     * </p>
     * Default Value: false<br>
     * Example Value: <code>true</code>
     **/
    public GeneralOptions useBeacon(boolean useBeacon) {
        putBoolean("useBeacon", useBeacon);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     *  Indicates the data source of the hit.
     *  Hits sent from analytics.js will have data source set to 'web';
     *  hits sent from one of the mobile SDKs will have data source set to 'app'.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>'crm'</code>
     **/
    public GeneralOptions dataSource(String dataSource) {
        putText("dataSource", dataSource);
        return this;
    }

    /**
     * <p>Disables a task.
     * see:
     * <a href="https://developers.google.com/analytics/devguides/collection/analyticsjs/tasks">Analytics Tasks</a>
     * </p>
     * <p>
     * If you are serving Analytics from a non http or https site,
     * eg because you are building a native app you need to disable
     * the {@link Task.CHECK_PROTOCOL} task
     * </p>
     * <p>
     * See also: <a href="http://dev.arcbees.com/ua/nativeApps.html">Analytics for Native Apps (Cordova etc..)</a>
     * </p>
     **/
    public GeneralOptions disableTask(Task task) {
        remove(task.getFunctionName());
        return this;
    }
}
