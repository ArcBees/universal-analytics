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

package com.arcbees.analytics.client;

public interface UniversalAnalytics {

    /**
     * Initializes the script for Google Analytics for a specific userAccount.
     *
     * @param userAccount The Google Analytics account. (i.e. {@code UA-12345678-1})
     */
    void init(String userAccount);

    /**
    *Enable SSL to force Google Analytics to always send data using SSL
    *
    * @param enableSSL a boolean value when set to true
     *                 indicates that SSL is used every time to send tracking data
    */
    void sslTracking(boolean enableSSL);

    /**
     * Given that you already have initialized your default Google account with
     * {@link #init(String)}, this function will add a new account on which you'll
     * be able to track events and pages.
     *
     * @param trackerName A user-defined tracker name.
     * @param userAccount The Google Analytics account. (i.e. {@code UA-12345678-1})
     */
    void addAccount(String trackerName, String userAccount);

    /**
     * Tracks the root of your application.
     */
    void trackPageview();

    /**
     * Tracks a page given a specific page name.
     *
     * @param pageName The page name to track.
     */
    void trackPageview(String pageName);

    /**
     * Tracks a page given a specific page name on a user defined tracker
     * name. See {@link #addAccount(String, String)}.
     *
     * @param trackerName A user defined tracker name.
     * @param pageName    The page name to track.
     */
    void trackPageview(String trackerName, String pageName);

    /**
     * Tracks an event in Universal analytics.
     *
     * @param category The name you supply for the group of objects you want to
     *                 track.
     * @param action   A string that is uniquely paired with each category, and
     *                 commonly used to define the type of user interaction for the web
     *                 object.
     */

    void trackEvent(String category, String action);

    /**
     * Tracks an event in Google analytics, attaching a label and value.
     *
     * @param category The name you supply for the group of objects you want to
     *                 track.
     * @param action   A string that is uniquely paired with each category, and
     *                 commonly used to define the type of user interaction for the web
     *                 object.
     * @param optLabel An string to provide additional dimensions to the event
     *                 data.
     */
    void trackEvent(String category, String action, String optLabel);

    /**
     * Tracks an event in Google analytics, attaching a label and value.
     *
     * @param category The name you supply for the group of objects you want to
     *                 track.
     * @param action   A string that is uniquely paired with each category, and
     *                 commonly used to define the type of user interaction for the web
     *                 object.
     * @param optLabel An string to provide additional dimensions to the event
     *                 data.
     * @param optValue An integer that you can use to provide numerical data about
     *                 the user event.
     */
    void trackEvent(String category, String action, String optLabel, int optValue);

    /**
     * Tracks an event in Google analytics, attaching a label and value.
     *
     * @param category          The name you supply for the group of objects you want to
     *                          track.
     * @param action            A string that is uniquely paired with each category, and
     *                          commonly used to define the type of user interaction for the web
     *                          object.
     * @param optLabel          An string to provide additional dimensions to the event
     *                          data.
     * @param optValue          An integer that you can use to provide numerical data about
     *                          the user event.
     * @param optNonInteraction An integer that when set to 1, indicates that the event hit
     *                          will not be used in bounce-rate calculation.
     */
    void trackEvent(String category, String action, String optLabel, int optValue, int optNonInteraction);
}
