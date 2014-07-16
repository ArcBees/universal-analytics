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

public class HitOptions extends AnalyticsOptions {
    HitOptions(final OptionsCallback<?> optionsCallback) {
        super(optionsCallback);
    }

    /**
    * <strong>Required for all hit types.</strong>
    * </p>
    * <p>The type of hit. Must be one of &#39;pageview&#39;, &#39;screenview&#39;, &#39;event&#39;, &#39;transaction&#39;, &#39;item&#39;, &#39;social&#39;, &#39;exception&#39;, &#39;timing&#39;.</p>
    * Default Value: None<br>
    * Example Value: <code>pageview</code>
    **/
    public HitOptions HitType(final String HitType) {
        putText("hitType", HitType);
        return this;
    }

    /**
    * Optional.
    * </p>
    * <p>Specifies that a hit be considered non-interactive.</p>
    * Default Value: None<br>
    * Example Value: <code>true</code>
    **/
    public HitOptions NonInteractionHit(final boolean NonInteractionHit) {
        putBoolean("nonInteraction", NonInteractionHit);
        return this;
    }
}
