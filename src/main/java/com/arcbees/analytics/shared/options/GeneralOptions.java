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

public class GeneralOptions extends AnalyticsOptions {
    GeneralOptions(final OptionsCallback<?> optionsCallback) {
        super(optionsCallback);
    }

    /**
     * Optional.
     * </p>
     * <p>When present, the IP address of the sender will be anonymized.</p>
     * Default Value: None<br>
     * Example Value: <code>true</code>
     **/
    public GeneralOptions anonymizeIp(final boolean anonymizeIp) {
        putBoolean("anonymizeIp", anonymizeIp);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>By default, tracking beacons sent from https pages will be sent using https
     * while beacons sent from http pages will be sent using http.
     * Setting forceSSL to true will force http pages to also send all beacons using https.</p>
     * Default Value: true<br>
     * Example Value: <code>true</code>
     **/
    public GeneralOptions forceSsl(final boolean forceSsl) {
        putBoolean("forceSSL", forceSsl);
        return this;
    }
}
