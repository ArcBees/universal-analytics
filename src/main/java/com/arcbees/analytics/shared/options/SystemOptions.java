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

public class SystemOptions extends AnalyticsOptions {
    SystemOptions(OptionsCallback<?> optionsCallback) {
        super(optionsCallback);
    }

    /**
     * Optional. </p>
     * <p>
     * Specifies the character set used to encode the page / document. This field is initialized by
     * the create command.
     * </p>
     * Default Value: UTF-8<br>
     * Example Value: <code>UTF-8</code>
     **/
    public SystemOptions documentEncoding(String documentEncoding) {
        putText("encoding", documentEncoding);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     * Specifies the flash version. This field is initialized by the create command.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>10 1 r103</code>
     **/
    public SystemOptions flashVersion(String flashVersion) {
        putText("flashVersion", flashVersion);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     * Specifies whether Java was enabled. This field is initialized by the create command.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>true</code>
     **/
    public SystemOptions javaEnabled(boolean javaEnabled) {
        putBoolean("javaEnabled", javaEnabled);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     * Specifies the screen color depth. This field is initialized by the create command.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>24-bits</code>
     **/
    public SystemOptions screenColors(String screenColors) {
        putText("screenColors", screenColors);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     * Specifies the screen resolution. This field is initialized by the create command.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>800x600</code>
     **/
    public SystemOptions screenResolution(String screenResolution) {
        putText("screenResolution", screenResolution);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     * Specifies the language. This field is initialized by the create command.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>en-us</code>
     **/
    public SystemOptions userLanguage(String userLanguage) {
        putText("language", userLanguage);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     * Specifies the viewable area of the browser / device. This field is initialized by the create
     * command.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>123x456</code>
     **/
    public SystemOptions viewportSize(String viewportSize) {
        putText("viewportSize", viewportSize);
        return this;
    }
}
