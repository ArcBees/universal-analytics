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

package com.arcbees.analytics.shared;

import com.arcbees.analytics.shared.options.AnalyticsOptions;
import com.arcbees.analytics.shared.options.ContentOptions;
import com.arcbees.analytics.shared.options.CreateOptions;
import com.arcbees.analytics.shared.options.EventsOptions;
import com.arcbees.analytics.shared.options.ExceptionOptions;
import com.arcbees.analytics.shared.options.GeneralOptions;
import com.arcbees.analytics.shared.options.SocialOptions;
import com.arcbees.analytics.shared.options.TimingOptions;

public interface Analytics {
    /**
     * Create a new tracker using the user id bound to GaAccount.<br>
     * Example: create().go();<br>
     * create().name("My Tracker").go() //create a custom named tracker
     */
    CreateOptions create();

    /**
     * Create a new tracker using a supplied user id.<br>
     * Example: create("UA-XXXXXXX-X").go();<br>
     * create("UA-XXXXXXX-X").name("My Tracker").go() //create a custom named tracker
     */
    CreateOptions create(String userAccount);

    /**
     * Enable an analytics plugin.  This must be called immediately after create();
     * If you are using plugins you should probably turn off autoCreate when building the
     * UniversalAnalyticsModule and create your tracker manually in your bootstrapper or EntryPoint.
     * 
     * @param plugin
     */
    void enablePlugin(AnalyticsPlugin plugin);

    /**
     * Used in conjuction with startTimingEvent to automatically setup and log timing events;
     * Call endTimingEvent with the same timingCategory and timingVariableName as you used in startTimingEvent()
     * to fire the event.
     * Calling this method before calling startTimingEvent will silently fail.
     * 
     * @param timingCategory
     * @param timingVariableName
     */
    TimingOptions endTimingEvent(String timingCategory, String timingVariableName);

    /**
     * Used in conjuction with startTimingEvent to automatically setup and log timing events;
     * Call endTimingEvent with the same timingCategory and timingVariableName as you used in startTimingEvent()
     * to fire the event.
     * Calling this method before calling startTimingEvent will silently fail.
     * 
     * @param timingCategory
     * @param timingVariableName
     */
    TimingOptions endTimingEvent(String trackerName, String timingCategory, String timingVariableName);

    /**
     * send a specific HitType.
     * 
     * @param hitType
     * see https://developers.google.com/analytics/devguides/collection/analyticsjs/field-reference#hit
     * Example: send(HitType.PAGE_VIEW).go();
     */
    AnalyticsOptions send(HitType hitType);

    /**
     * send a specific HitType to a specific tracker.
     * 
     * @param trackerName the name of the tracker
     * @param hitType
     * see https://developers.google.com/analytics/devguides/collection/analyticsjs/field-reference#hit
     * Example: send(HitType.PAGE_VIEW).go();
     */
    AnalyticsOptions send(String trackerName, HitType hitType);

    /**
     * send an event.
     * 
     * @param category  the event category
     * @param action the event action<br>
     * see https://developers.google.com/analytics/devguides/collection/analyticsjs/field-reference#events
     * Example: sendEvent("button", "click").go();
     */
    EventsOptions sendEvent(String category, String action);

    /**
     * send an event to a specific tracker.
     * 
     * @param trackerName the name of the tracker
     * @param category  the event category
     * @param action the event action<br>
     * see https://developers.google.com/analytics/devguides/collection/analyticsjs/field-reference#events
     * Example: sendEvent("button", "click").go();
     */
    EventsOptions sendEvent(String trackerName, String category, String action);

    /**
     * Track an exception to a specific tracker.
     * By default the description
     * will be filled out with the exception's stack trace.
     * @param trackerName
     * @param e - the exception to track
     * @return
     */
    ExceptionOptions sendException(String trackerName, Throwable e);

    /**
     * Track an exception.
     * By default the description
     * will be filled out with the exception's stack trace.
     * @param e - the exception to track
     * @return
     */
    ExceptionOptions sendException(Throwable e);

    /**
     * send a pageview to a specific tracker.
     * Example: sendPageView().go();<br>
     * sendPageView().documentPath("/foo").go(); //send a pageview for /foo
     */
    ContentOptions sendPageView();

    /**
     * send a pageview to a specific tracker.
     * 
     * @param trackerName the name of the tracker
     * Example: sendPageView();<br>
     * sendPageView().documentPath("/foo").go(); //send a pageview for /foo
     */
    ContentOptions sendPageView(String trackerName);

    /**
     * send a social event.
     * 
     * @param socialNetwork the social network
     * @param socialAction the action taken
     * @param socialTarget the target of the action
     * Example: sendSocial("facebook", "like", "http://www.example.com").go();<br>
     */
    SocialOptions sendSocial(String socialNetwork, String socialAction, String socialTarget);

    /**
     * send a social event to a specific tracker.
     * 
     * @param trackerName the name of the tracker
     * @param socialNetwork the social network
     * @param socialAction the action taken
     * @param socialTarget the target of the action
     * Example: sendSocial("facebook", "like", "http://www.example.com").go();<br>
     */
    SocialOptions sendSocial(String trackerName, String socialNetwork, String socialAction, String socialTarget);

    /**
     * send user timing information to the default tracker.
     * this is used to analyze page speed.
     * 
     * @param timingCategory - a category used to group related timing data
     * @param timingVar - a string to identify the variable being recorded
     * @param timingValue - the number of milliseconds of elapsed time.
     * Example: sendTiming("jQuery", "Load Library", 20).go();<br>
     */
    TimingOptions sendTiming(final String timingCategory, final String timingVar, final int timingValue);

    /**
     * send user timing information to a specific tracker.
     * this is used to analyze page speed.
     * 
     * @param trackerName - the name of the tracker
     * @param timingCategory - a category used to group related timing data
     * @param timingVar - a string to identify the variable being recorded
     * @param timingValue - the number of milliseconds of elapsed time.
     * Example: sendTiming("jQuery", "Load Library", 20).go();<br>
     */
    TimingOptions sendTiming(String trackerName, final String timingCategory, final String timingVar,
            final int timingValue);

    /**
     * set options for all subsequent calls.
     * Example: setGlobalSettings().anonymizeIp(true).go(); //anonymize ip addresses<br>
     */
    GeneralOptions setGlobalSettings();

    /**
     * A handy shortcut for setting up an event you want to time.
     * On its own this method does nothing.
     * Call endTimingEvent with the same timingCategory and timingVariableName to fire the event.
     * 
     * @param timingCategory
     * @param timingVariableName
     */
    void startTimingEvent(String timingCategory, String timingVariableName);
}
