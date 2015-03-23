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

public enum AnalyticsPlugin {
    /**
     * Add information about user's location and interests. You also need to turn on Enable
     * Demographics and Interest Reports on the google analytics website to use this feature.
     */
    DISPLAY("displayfeatures"),

    /**
     * You must also turn on enhanced link attribution on the google analytics website to use this
     * feature.
     * <p>
     * You can tag your pages to implement an enhanced link-tracking functionality that lets you:
     * </p>
     *
     * <ul>
     * <li>See separate information for multiple links on a page that all have the same destination.
     * For example, if there are two links on the same page that both lead to the
     * <em>Contact Us</em> page, then you see separate click information for each link.</li>
     * <br>
     * <li>See when one page element has multiple destinations. For example, a
     * <em>Search button</em> on your page is likely to lead to multiple destinations.</li>
     * <br>
     * <li>Track buttons, menus, and actions driven by javascript.</li>
     * </ul>
     */
    ENHANCED_LINK_ATTRIBUTION("linkid", "linkid.js");

    private final String fieldName;
    private final String jsName;

    AnalyticsPlugin(String fieldName) {
        this(fieldName, null);
    }

    AnalyticsPlugin(String fieldName, String jsName) {
        this.fieldName = fieldName;
        this.jsName = jsName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getJsName() {
        return jsName;
    }
}
