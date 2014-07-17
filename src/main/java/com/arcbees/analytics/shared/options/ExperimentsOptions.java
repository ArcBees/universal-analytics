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

public class ExperimentsOptions extends AnalyticsOptions {
    ExperimentsOptions(final OptionsCallback<?> optionsCallback) {
        super(optionsCallback);
    }

    /**
     * Optional.
     * </p>
     * <p>This parameter specifies that this user has been exposed to an experiment with the given ID. It should be sent in conjunction with the Experiment Variant parameter.</p>
     * Default Value: None<br>
     * Example Value: <code>Qp0gahJ3RAO3DJ18b0XoUQ</code>
     **/
    public ExperimentsOptions experimentId(final String experimentId) {
        putText("expId", experimentId);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>This parameter specifies that this user has been exposed to a particular variation of an experiment. It should be sent in conjunction with the Experiment ID parameter.</p>
     * Default Value: None<br>
     * Example Value: <code>1</code>
     **/
    public ExperimentsOptions experimentVariant(final String experimentVariant) {
        putText("expVar", experimentVariant);
        return this;
    }
}
