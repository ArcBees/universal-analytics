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

package com.arcbees.analytics.server.options;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.arcbees.analytics.shared.HitCallback;
import com.arcbees.analytics.shared.options.OptionsCallback;

public abstract class TrackerNameOptionsCallback extends OptionsCallback<String> {
    private Map<String, String> options = new ConcurrentHashMap<>();

    @Override
    public void addHitCallback(HitCallback hitCallback) {
        hitCallback.onCallback();
    }

    @Override
    public String getOptions() {
        return options.get("name");
    }

    @Override
    public void putBoolean(String fieldName, boolean value) {
        putText(fieldName, value ? "1" : "0");
    }

    @Override
    public void putNumber(String fieldName, double value) {
        putText(fieldName, value + "");
    }

    @Override
    public void putText(String fieldName, String value) {
        options.put(fieldName, value);
    }
}
