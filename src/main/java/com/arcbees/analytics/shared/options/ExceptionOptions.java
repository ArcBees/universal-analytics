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

public class ExceptionOptions extends AnalyticsOptions {
    ExceptionOptions(final OptionsCallback<?> optionsCallback) {
        super(optionsCallback);
    }

    /**
    * Optional.
    * </p>
    * <p>Specifies the description of an exception.</p>
    * Default Value: None<br>
    * Example Value: <code>DatabaseError</code>
    **/
    public ExceptionOptions ExceptionDescription(final String ExceptionDescription) {
        putText("exDescription", ExceptionDescription);
        return this;
    }

    /**
    * Optional.
    * </p>
    * <p>Specifies whether the exception was fatal.</p>
    * Default Value: 1<br>
    * Example Value: <code>true</code>
    **/
    public ExceptionOptions IsExceptionFatal(final boolean IsExceptionFatal) {
        putBoolean("exFatal", IsExceptionFatal);
        return this;
    }
}
