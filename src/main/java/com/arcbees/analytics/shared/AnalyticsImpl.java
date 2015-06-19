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
import com.arcbees.analytics.shared.options.SocialOptions;
import com.arcbees.analytics.shared.options.TimingOptions;

public abstract class AnalyticsImpl implements Analytics {
    private String userAccount;

    protected AnalyticsImpl(String userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public CreateOptions create() {
        return create(userAccount);
    }

    @Override
    public TimingOptions endTimingEvent(String timingCategory, String timingVariableName) {
        return endTimingEvent(null, timingCategory, timingVariableName);
    }

    protected String getTimingKey(String timingCategory, String timingVariableName) {
        return timingCategory + ":" + timingVariableName;
    }

    @Override
    public AnalyticsOptions send(HitType hitType) {
        return send(null, hitType);
    }

    @Override
    public EventsOptions sendEvent(String category, String action) {
        return sendEvent(null, category, action);
    }

    @Override
    public EventsOptions sendEvent(String trackerName, String category,
            String action) {
        return send(trackerName, HitType.EVENT).eventsOptions(category, action);
    }

    @Override
    public ExceptionOptions sendException() {
        return sendException(null);
    }

    @Override
    public ExceptionOptions sendException(String trackerName) {
        return send(trackerName, HitType.EXCEPTION).exceptionOptions();
    }

    @Override
    public ContentOptions sendPageView() {
        return sendPageView(null);
    }

    @Override
    public ContentOptions sendPageView(String trackerName) {
        return send(trackerName, HitType.PAGE_VIEW).contentOptions();
    }

    @Override
    public ContentOptions sendScreenView() {
        return sendScreenView(null);
    }

    @Override
    public ContentOptions sendScreenView(String trackerName) {
        return send(trackerName, HitType.SCREEN_VIEW).contentOptions();
    }

    @Override
    public SocialOptions sendSocial(String socialNetwork, String socialAction,
            String socialTarget) {
        return sendSocial(null, socialNetwork, socialAction, socialTarget);
    }

    @Override
    public SocialOptions sendSocial(String trackerName, String socialNetwork,
            String socialAction, String socialTarget) {
        return send(trackerName, HitType.SOCIAL).socialOptions(socialNetwork, socialAction,
                socialTarget);
    }

    @Override
    public TimingOptions sendTiming(String timingCategory, String timingVar,
            int timingValue) {
        return sendTiming(null, timingCategory, timingVar, timingValue);
    }

    @Override
    public TimingOptions sendTiming(String trackerName, String timingCategory,
            String timingVar, int timingValue) {
        return send(trackerName, HitType.TIMING).timingOptions(timingCategory, timingVar,
                timingValue);
    }
}
