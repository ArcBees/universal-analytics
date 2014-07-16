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
    SystemOptions(final OptionsCallback<?> optionsCallback) {
        super(optionsCallback);

    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the character set used to encode the page / document. This field is initialized by the create command.</p>
     * Default Value: UTF-8<br>
     * Example Value: <code>UTF-8</code>
     **/
    public SystemOptions DocumentEncoding(final String DocumentEncoding) {
        putText("encoding", DocumentEncoding);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the flash version. This field is initialized by the create command.</p>
     * Default Value: None<br>
     * Example Value: <code>10 1 r103</code>
     **/
    public SystemOptions FlashVersion(final String FlashVersion) {
        putText("flashVersion", FlashVersion);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies whether Java was enabled. This field is initialized by the create command.</p>
     * Default Value: None<br>
     * Example Value: <code>true</code>
     **/
    public SystemOptions JavaEnabled(final boolean JavaEnabled) {
        putBoolean("javaEnabled", JavaEnabled);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the screen color depth. This field is initialized by the create command.</p>
     * Default Value: None<br>
     * Example Value: <code>24-bits</code>
     **/
    public SystemOptions ScreenColors(final String ScreenColors) {
        putText("screenColors", ScreenColors);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the screen resolution. This field is initialized by the create command.</p>
     * Default Value: None<br>
     * Example Value: <code>800x600</code>
     **/
    public SystemOptions ScreenResolution(final String ScreenResolution) {
        putText("screenResolution", ScreenResolution);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the language. This field is initialized by the create command.</p>
     * Default Value: None<br>
     * Example Value: <code>en-us</code>
     **/
    public SystemOptions UserLanguage(final String UserLanguage) {
        putText("language", UserLanguage);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the viewable area of the browser / device. This field is initialized by the create command.</p>
     * Default Value: None<br>
     * Example Value: <code>123x456</code>
     **/
    public SystemOptions ViewportSize(final String ViewportSize) {
        putText("viewportSize", ViewportSize);
        return this;
    }
}
