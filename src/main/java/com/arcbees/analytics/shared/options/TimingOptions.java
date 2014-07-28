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

public class TimingOptions extends AnalyticsOptions {
    TimingOptions(final OptionsCallback<?> optionsCallback,
            final String UserTimingCategory,
            final String userTimingVariableName,
            final int userTimingTime) {
        super(optionsCallback);

        putText("timingCategory", UserTimingCategory);
        putText("timingVar", userTimingVariableName);
        putNumber("timingValue", userTimingTime);
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the user timing label.</p>
     * Default Value: None<br>
     * Example Value: <code>label</code>
     **/
    public TimingOptions userTimingLabel(final String userTimingLabel) {
        putText("timingLabel", userTimingLabel);
        return this;
    }
}
