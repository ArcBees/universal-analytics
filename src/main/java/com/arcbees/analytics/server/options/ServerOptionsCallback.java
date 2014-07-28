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
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.arcbees.analytics.shared.HitCallback;
import com.arcbees.analytics.shared.options.OptionsCallback;

public class ServerOptionsCallback extends OptionsCallback<String> {
    private static final Map<String, String> protocolMap = new HashMap<>();

    static {
        protocolMap.put("anonymizeIp", "aip");
        protocolMap.put("forceSSL", "None");
        protocolMap.put("hitCallback", "None");
        protocolMap.put("referrer", "dr");
        protocolMap.put("campaignName", "cn");
        protocolMap.put("campaignSource", "cs");
        protocolMap.put("campaignMedium", "cm");
        protocolMap.put("campaignKeyword", "ck");
        protocolMap.put("campaignContent", "cc");
        protocolMap.put("campaignId", "ci");
        protocolMap.put("screenResolution", "sr");
        protocolMap.put("viewportSize", "vp");
        protocolMap.put("encoding", "de");
        protocolMap.put("screenColors", "sd");
        protocolMap.put("language", "ul");
        protocolMap.put("javaEnabled", "je");
        protocolMap.put("flashVersion", "fl");
        protocolMap.put("hitType", "t");
        protocolMap.put("nonInteraction", "ni");
        protocolMap.put("location", "dl");
        protocolMap.put("hostname", "dh");
        protocolMap.put("page", "dp");
        protocolMap.put("title", "dt");
        protocolMap.put("screenName", "cd");
        protocolMap.put("linkid", "linkid");
        protocolMap.put("appName", "an");
        protocolMap.put("appId", "aid");
        protocolMap.put("appVersion", "av");
        protocolMap.put("appInstallerId", "aiid");
        protocolMap.put("eventCategory", "ec");
        protocolMap.put("eventAction", "ea");
        protocolMap.put("eventLabel", "el");
        protocolMap.put("eventValue", "ev");
        protocolMap.put("socialNetwork", "sn");
        protocolMap.put("socialAction", "sa");
        protocolMap.put("socialTarget", "st");
        protocolMap.put("timingCategory", "utc");
        protocolMap.put("timingVar", "utv");
        protocolMap.put("timingValue", "utt");
        protocolMap.put("timingLabel", "utl");
        protocolMap.put("exDescription", "exd");
        protocolMap.put("exFatal", "exf");
        protocolMap.put("expId", "xid");
        protocolMap.put("expVar", "xvar");
    }

    private final static Logger logger = Logger.getLogger(ServerOptionsCallback.class.getName());
    private static final String POST_URL = "http://www.google-analytics.com/collect";

    private Map<String, String> options = new HashMap<>();

    @Override
    public void addHitCallback(final HitCallback hitCallback) {
        hitCallback.onCallback();
    }

    @Override
    public String getOptions() {
        final StringBuilder result = new StringBuilder();

        for (final Entry<String, String> entry : options.entrySet()) {
            result.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        return "?" + result.toString().substring(1);
    }

    private String getProtocolFieldName(final String fieldName) {
        if (fieldName.startsWith("metric")) {
            return "cm" + fieldName.substring("metric".length());
        } else if (fieldName.startsWith("dimension")) {
            return "cd" + fieldName.substring("dimension".length());
        } else if (protocolMap.containsKey(fieldName)) {
            return protocolMap.get(fieldName);
        } else {
            return fieldName;
        }
    }

    @Override
    public void onCallback(final String options) {
        try {
            final URL url = new URL(POST_URL + options);
            IOUtils.toString(url);
        } catch (final IOException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void putBoolean(final String fieldName, final boolean value) {
        putText(fieldName, value ? "1" :"0");
    }

    @Override
    public void putNumber(final String fieldName, final double value) {
        putText(fieldName, value + "");
    }

    @Override
    public void putText(final String fieldName, final String value) {
        options.put(getProtocolFieldName(fieldName), value);
    }
}
