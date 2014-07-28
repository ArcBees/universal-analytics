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

public class CustomsOptions extends AnalyticsOptions {
    CustomsOptions(final OptionsCallback<?> optionsCallback) {
        super(optionsCallback);
    }

    /**
     * Optional.
     * </p>
     * <p>Each custom dimension has an associated index.
     * There is a maximum of 20 custom dimensions (200 for Premium accounts).
     * The name suffix must be a positive integer between 1 and 200, inclusive.</p>
     * Default Value: None<br>
     * Example Usage: <code>CustomDimension(14, Sports)</code>
     **/
    public CustomsOptions customDimension(final int dimension, final String value) {
        putText("dimension" + dimension, value);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Each custom metric has an associated index.
     * There is a maximum of 20 custom metrics (200 for Premium accounts).
     * The name suffix must be a positive integer between 1 and 200, inclusive.</p>
     * Default Value: None<br>
     * Example Usage: <code>CustomMetric(7, 47)</code>
     **/
    public CustomsOptions customMetric(final int metric, final int value) {
        putNumber("metric" + metric, value);
        return this;
    }
}
