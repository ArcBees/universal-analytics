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

public abstract class OptionsCallback<T> {
    public abstract void addHitCallback(HitCallback hitCallback);

    public abstract T getOptions();

    public abstract void onCallback(T options);

    public abstract void putBoolean(String fieldName, boolean value);

    public abstract void putNumber(String fieldName, double value);

    public abstract void putText(String fieldName, String value);

    void doCallback() {
        onCallback(getOptions());
    }

    public void remove(String fieldName) {
        putText(fieldName, null);
    }
}
