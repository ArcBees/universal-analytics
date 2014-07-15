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
package com.arcbees.analytics.client.universalanalytics;

import com.arcbees.analytics.client.GoogleAnalytics;
import com.arcbees.analytics.client.universalanalytics.fields.ContentFieldBuilder;
import com.arcbees.analytics.client.universalanalytics.fields.CreateOnlyFieldBuilder;
import com.arcbees.analytics.client.universalanalytics.fields.EventFieldBuilder;
import com.arcbees.analytics.client.universalanalytics.fields.FieldBuilder;
import com.arcbees.analytics.client.universalanalytics.fields.SocialNetworkFieldBuilder;
import com.arcbees.analytics.client.universalanalytics.fields.UserTimingFieldBuilder;

public interface UniversalAnalytics extends GoogleAnalytics {
    /**
     * Create a new tracker using the user id bound to GaAccount.<br>
     * Example: create();<br>
     * create().name("My Tracker") //create a custom named tracker
     */
    CreateOnlyFieldBuilder create();

    /**
     * Create a new tracker using a supplied user id.<br>
     * Example: create("UA-XXXXXXX-X");<br>
     * create("UA-XXXXXXX-X").name("My Tracker") //create a custom named tracker
     */
    CreateOnlyFieldBuilder create(String userAccount);

    /**
     * Enable an analytics plugin.  This must be called immediately after create();
     * If you are using plugins you should probably turn off autoCreate when building the
     * UniversalAnalyticsModule and create your tracker manually in your bootstrapper.
     * @param plugin
     */
    void enablePlugin(AnalyticsPlugin plugin);

    /**
     * send a specific HitType.
     * @param hitType
     * see https://developers.google.com/analytics/devguides/collection/analyticsjs/field-reference#hit
     * Example: send(HitType.PAGE_VIEW);
     */
    FieldBuilder send(HitType hitType);

    /**
     * send a specific HitType to a specific tracker.
     * @param trackerName the name of the tracker
     * @param hitType
     * see https://developers.google.com/analytics/devguides/collection/analyticsjs/field-reference#hit
     * Example: send(HitType.PAGE_VIEW);
     */
    FieldBuilder send(String trackerName, HitType hitType);

    /**
     * send an event.
     * @param category  the event category
     * @param action the event action<br>
     * see https://developers.google.com/analytics/devguides/collection/analyticsjs/field-reference#events
     * Example: sendEvent("button", "click");
     */
    EventFieldBuilder sendEvent(String category, String action);

    /**
     * send an event to a specific tracker.
     * @param trackerName the name of the tracker
     * @param category  the event category
     * @param action the event action<br>
     * see https://developers.google.com/analytics/devguides/collection/analyticsjs/field-reference#events
     * Example: sendEvent("button", "click");
     */
    EventFieldBuilder sendEvent(String trackerName, String category, String action);

    /**
     * send a pageview to a specific tracker.
     * Example: sendPageView();<br>
     * sendPageView().documentPath("/foo"); //send a pageview for /foo
     */
    ContentFieldBuilder sendPageView();

    /**
     * send a pageview to a specific tracker.
     * @param trackerName the name of the tracker
     * Example: sendPageView();<br>
     * sendPageView().documentPath("/foo"); //send a pageview for /foo
     */
    ContentFieldBuilder sendPageView(String trackerName);

    /**
     * send a social event.
     * @param socialNetwork the social network
     * @param socialAction the action taken
     * @param socialTarget the target of the action
     * Example: sendSocial("facebook", "like", "http://www.example.com");<br>
     */
    SocialNetworkFieldBuilder sendSocial(String socialNetwork, String socialAction, String socialTarget);

    /**
     * send a social event to a specific tracker.
     * @param trackerName the name of the tracker
     * @param socialNetwork the social network
     * @param socialAction the action taken
     * @param socialTarget the target of the action
     * Example: sendSocial("facebook", "like", "http://www.example.com");<br>
     */
    SocialNetworkFieldBuilder sendSocial(String trackerName, String socialNetwork, String socialAction,
            String socialTarget);

    /**
     * send user timing information to the default tracker.
     * this is use to analyze page speed.
     * @param timingCategory - a category used to group related timing data
     * @param timingVar - a string to identify the variable being recorded
     * @param timingValue - the number of milliseconds of elapsed time.
     * Example: sendTiming("jQuery", "Load Library", 20);<br>
     */
    UserTimingFieldBuilder sendTiming(final String timingCategory, final String timingVar, final int timingValue);

    /**
     * send user timing information to a specific tracker.
     * this is use to analyze page speed.
     * @param trackerName - the name of the tracker
     * @param timingCategory - a category used to group related timing data
     * @param timingVar - a string to identify the variable being recorded
     * @param timingValue - the number of milliseconds of elapsed time.
     * Example: sendTiming("jQuery", "Load Library", 20);<br>
     */
    UserTimingFieldBuilder sendTiming(String trackerName, final String timingCategory, final String timingVar,
            final int timingValue);

    /**
     * set options for all subsequent calls.
     * Example: setGlobalSettings().general().anonymizeIp(true); //anonymize ip addresses<br>
     */
    FieldBuilder setGlobalSettings();
}
