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

import com.arcbees.analytics.shared.HitCallback;
import com.arcbees.analytics.shared.options.OptionsCallback;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public abstract class JSONOptionsCallback extends OptionsCallback<JSONObject> {
    private JSONObject jsonObject;

    public JSONOptionsCallback() {
        this.jsonObject = new JSONObject();
    }

    @Override
    public void addHitCallback(final HitCallback callback) {
        addHitCallback(jsonObject, new GuaranteedHitCallback(callback));
    }

    private native void addHitCallback(JSONObject jsonObject, HitCallback callback) /*-{
        jsonObject.hitCallback = function() {
            callback.@com.arcbees.analytics.shared.HitCallback::onCallback()();
        }
    }-*/;

    @Override
    public JSONObject getOptions() {
        return jsonObject;
    }

    @Override
    public void putBoolean(final String fieldName, final boolean value) {
        jsonObject.put(fieldName, JSONBoolean.getInstance(value));
    }

    @Override
    public void putNumber(final String fieldName, final double value) {
        jsonObject.put(fieldName, new JSONNumber(value));
    }

    @Override
    public void putText(final String fieldName, final String value) {
        jsonObject.put(fieldName, new JSONString(value));
    }
}
