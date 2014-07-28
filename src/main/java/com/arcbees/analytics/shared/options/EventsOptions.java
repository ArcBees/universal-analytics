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

public class EventsOptions extends AnalyticsOptions {
    EventsOptions(final OptionsCallback<?> optionsCallback, final String eventCategory, final String eventAction) {
        super(optionsCallback);

        putText("eventCategory", eventCategory);
        putText("eventAction", eventAction);
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the event label.</p>
     * Default Value: None<br>
     * Example Value: <code>Label</code>
     **/
    public EventsOptions eventLabel(final String eventLabel) {
        putText("eventLabel", eventLabel);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the event value. Values must be non-negative.</p>
     * Default Value: None<br>
     * Example Value: <code>55</code>
     **/
    public EventsOptions eventValue(final int eventValue) {
        putNumber("eventValue", eventValue);
        return this;
    }
}
