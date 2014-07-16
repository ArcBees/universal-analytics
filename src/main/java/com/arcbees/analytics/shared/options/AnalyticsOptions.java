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

import com.arcbees.analytics.shared.HitCallback;

public class AnalyticsOptions {

    private final OptionsCallback<?> optionsCallback;

    public AnalyticsOptions(final OptionsCallback<?> optionsCallback) {
        this.optionsCallback = optionsCallback;
    }

    public ApptrackingOptions apptrackingOptions() {
        return new ApptrackingOptions(getOptionsCallback());
    }

    public ContentOptions contentOptions() {
        return new ContentOptions(getOptionsCallback());
    }

    public CreateOptions createOptions() {
        return new CreateOptions(getOptionsCallback());
    }

    public CustomsOptions customsOptions() {
        return new CustomsOptions(getOptionsCallback());
    }

    public EventsOptions eventsOptions(final String eventCategory, final String eventAction) {
        return new EventsOptions(getOptionsCallback(), eventCategory, eventAction);
    }

    public ExceptionOptions exceptionOptions() {
        return new ExceptionOptions(getOptionsCallback());
    }

    public ExperimentsOptions experimentsOptions() {
        return new ExperimentsOptions(getOptionsCallback());
    }

    public GeneralOptions generalOptions() {
        return new GeneralOptions(getOptionsCallback());
    }

    OptionsCallback<?> getOptionsCallback() {
        return optionsCallback;
    }

    /**
     * Perform the call
     **/
    public void go() {
        this.optionsCallback.doCallback();
    }

    public void go(final HitCallback hitCallback) {
        optionsCallback.addHitCallback(hitCallback);
        go();
    }

    public HitOptions hitOptions() {
        return new HitOptions(getOptionsCallback());
    }

    void putBoolean(final String fieldName, final boolean value) {
        optionsCallback.putBoolean(fieldName, value);
    }

    void putNumber(final String fieldName, final double value) {
        optionsCallback.putNumber(fieldName, value);
    }

    void putText(final String fieldName, final String value) {
        optionsCallback.putText(fieldName, value);
    }

    public SocialOptions socialOptions(final String socialNetwork, final String socialAction, final String socialTarget) {
        return new SocialOptions(getOptionsCallback(), socialNetwork, socialAction, socialTarget);
    }

    public SystemOptions systemOptions() {
        return new SystemOptions(getOptionsCallback());
    }

    public TimingOptions timingOptions(final String timingCategory, final String timingVar, final int timingValue) {
        return new TimingOptions(getOptionsCallback(), timingCategory, timingVar, timingValue);
    }

    public TrafficsourcesOptions trafficsourcesOptions() {
        return new TrafficsourcesOptions(getOptionsCallback());
    };

}
