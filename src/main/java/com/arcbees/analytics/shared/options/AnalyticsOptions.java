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

import java.util.logging.Level;
import java.util.logging.Logger;

import com.arcbees.analytics.shared.HitCallback;

public class AnalyticsOptions {
    private final static Logger logger = Logger.getLogger("Universal Analytics");
    private final OptionsCallback<?> optionsCallback;

    public AnalyticsOptions(final OptionsCallback<?> optionsCallback) {
        this.optionsCallback = optionsCallback;
    }

    /**
     * Allows you to set options in the AppTracking Category
     */
    public ApptrackingOptions apptrackingOptions() {
        return new ApptrackingOptions(getOptionsCallback());
    }

    /**
     * Allows you to set options in the Content Category
     */
    public ContentOptions contentOptions() {
        return new ContentOptions(getOptionsCallback());
    }

    /**
     * Allows you to set options for create calls.
     * The options in this category will have no effect on non create calls.
     */
    public CreateOptions createOptions() {
        return new CreateOptions(getOptionsCallback());
    }

    /**
     * Allows you to set options for custom metrics
     */
    public CustomsOptions customsOptions() {
        return new CustomsOptions(getOptionsCallback());
    }

    /**
     * Set Options for an event call.
     * In general you should let the analytics class set this up for you with the sendEvent() method.
     */
    public EventsOptions eventsOptions(final String eventCategory, final String eventAction) {
        return new EventsOptions(getOptionsCallback(), eventCategory, eventAction);
    }

    /**
     * Allows you to set options for tracking exceptions.
     */
    public ExceptionOptions exceptionOptions() {
        return new ExceptionOptions(getOptionsCallback());
    }

    /**
     * Allows you to set options for tracking experiments.
     */
    public ExperimentsOptions experimentsOptions() {
        return new ExperimentsOptions(getOptionsCallback());
    }

    /**
     * General options eg. anonymize ip.  forceSSL etc.
     */
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

    /**
     * Perform the call and then return to the hitcallback when finished.
     * On the server the hitcallback will return before the call is made.
     * On the client the hitcallback is guaranteed to return within 350ms whether or not the call has completed.
     * @param hitCallback
     */
    public void go(final HitCallback hitCallback) {
        optionsCallback.addHitCallback(hitCallback);
        go();
    }

    /**
     * Perform the call and log it, useful for debugging
     * or for example if you want to see a timing event in your console.
     * @param level
     */
    public void goAndLog(final Level level) {
        logger.log(level, optionsCallback.getOptions().toString());
        go();
    }

    /**
     * Allows you to set options related to the type of hit.
     * eg. NonInteractionHit for events not initiated by the user.
     */
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

    /**
     * Set Options for tracking a social event.
     * In general you should let the analytics class set this up for you with the sendSocial() method.
     */
    public SocialOptions socialOptions(final String socialNetwork, final String socialAction,
            final String socialTarget) {
        return new SocialOptions(getOptionsCallback(), socialNetwork, socialAction, socialTarget);
    }

    /**
     * Allows you to set system options. eg. whether Java is enabled.
     */
    public SystemOptions systemOptions() {
        return new SystemOptions(getOptionsCallback());
    }

    /**
     * Set Options for tracking a timing event.
     * In general you should let the analytics class set this up for you with the sendTiming() method.
     */
    public TimingOptions timingOptions(final String timingCategory, final String timingVar, final int timingValue) {
        return new TimingOptions(getOptionsCallback(), timingCategory, timingVar, timingValue);
    }

    /**
     * Set Options for tracking a traffic sources.  This can be used to track the source of user traffic.
     */
    public TrafficsourcesOptions trafficsourcesOptions() {
        return new TrafficsourcesOptions(getOptionsCallback());
    };
}
