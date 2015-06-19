/**
 * Copyright 2015 ArcBees Inc.
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

public enum Task {
    /**
     * Aborts the request if the page is only being rendered to generate
     * a 'Top Sites' thumbnail for Safari.
     */
    PREVIEW("previewTask"),
    /**
     * Aborts the request if the page protocol is not http or https.
     */
    CHECK_PROTOCOL("checkProtocolTask"),
    /**
     * Aborts the request if required fields are missing or invalid.
     */
    VALIDATION("validationTask"),
    /**
     * Aborts the request if the tracker is configured to use cookies
     * but the user's browser has cookies disabled.
     */
    CHECK_STORAGE("checkStorageTask"),
    /**
     * Imports information from ga.js and urchin.js cookies to preserve
     * visitor history when a site migrates to Universal Analytics.
     */
    HISTORY_IMPORTER("historyImportTask"),
    /**
     * Samples out visitors based on the sampleRate setting for this tracker.
     */
    SAMPLER("samplerTask"),
    /**
     * Builds a measurement protocol request string and stores it in the hitPayload field.
     */
    BUILD_HIT("buildHitTask"),
    /**
     * Transmits the measurement protocol request stored in the hitPayload
     * field to Google Analytics servers.
     */
    SEND_HIT("sendHitTask"),
    /**
     * Automatically generates a site speed timing beacon based on
     * the siteSpeedSampleRate setting for this tracker.
     */
    TIMING("timingTask");

    private final String functionName;

    Task(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }
}
