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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.arcbees.analytics.shared.HitCallback;
import com.arcbees.analytics.shared.ProtocolTranslator;
import com.arcbees.analytics.shared.options.OptionsCallback;

public class ServerOptionsCallback extends OptionsCallback<String> {
    private static final Logger LOGGER = Logger.getLogger(ServerOptionsCallback.class.getName());
    private static final String POST_URL = "https://www.google-analytics.com/collect";

    private Map<String, String> options = new HashMap<>();

    @Override
    public void addHitCallback(HitCallback hitCallback) {
        hitCallback.onCallback();
    }

    @Override
    public String getOptions() {
        StringBuilder result = new StringBuilder();

        for (Entry<String, String> entry : options.entrySet()) {
            String key = entry.getKey();
            String value = encodeQueryParam(entry.getValue());

            result.append("&").append(key).append("=").append(value);
        }

        return "?" + result.toString().substring(1);
    }

    @Override
    public void onCallback(String options) {
        try {
            final URL url = new URL(POST_URL + options);
            IOUtils.toString(url);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public synchronized void putBoolean(String fieldName, boolean value) {
        putText(fieldName, value ? "1" : "0");
    }

    @Override
    public synchronized void putNumber(String fieldName, double value) {
        putText(fieldName, value + "");
    }

    @Override
    public synchronized void putText(String fieldName, String value) {
        if (value == null) {
            options.remove(ProtocolTranslator.getFieldName(fieldName));
        } else {
            options.put(ProtocolTranslator.getFieldName(fieldName), value);
        }
    }

    private String encodeQueryParam(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.log(
                    Level.WARNING,
                    "Unable to encode the analytics parameters to UTF-8. Falling back to no encoding.",
                    e);

            return value;
        }
    }
}
