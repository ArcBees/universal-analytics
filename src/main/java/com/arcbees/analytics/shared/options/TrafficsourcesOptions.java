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

public class TrafficsourcesOptions extends AnalyticsOptions {
    TrafficsourcesOptions(final OptionsCallback<?> optionsCallback) {
        super(optionsCallback);
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the campaign content.</p>
     * Default Value: None<br>
     * Example Value: <code>content</code>
     **/
    public TrafficsourcesOptions campaignContent(final String campaignContent) {
        putText("campaignContent", campaignContent);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the campaign ID.</p>
     * Default Value: None<br>
     * Example Value: <code>ID</code>
     **/
    public TrafficsourcesOptions campaignId(final String campaignId) {
        putText("campaignId", campaignId);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the campaign keyword.</p>
     * Default Value: None<br>
     * Example Value: <code>Blue Shoes</code>
     **/
    public TrafficsourcesOptions campaignKeyword(final String campaignKeyword) {
        putText("campaignKeyword", campaignKeyword);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the campaign medium.</p>
     * Default Value: None<br>
     * Example Value: <code>organic</code>
     **/
    public TrafficsourcesOptions campaignMedium(final String campaignMedium) {
        putText("campaignMedium", campaignMedium);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the campaign name.</p>
     * Default Value: None<br>
     * Example Value: <code>(direct)</code>
     **/
    public TrafficsourcesOptions campaignName(final String campaignName) {
        putText("campaignName", campaignName);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies the campaign source.</p>
     * Default Value: None<br>
     * Example Value: <code>(direct)</code>
     **/
    public TrafficsourcesOptions campaignSource(final String campaignSource) {
        putText("campaignSource", campaignSource);
        return this;
    }

    /**
     * Optional.
     * </p>
     * <p>Specifies which referral source brought traffic to a website.
     * This value is also used to compute the traffic source.
     * The format of this value is a URL.
     * This field is initialized by the create command and is only set when the current
     * hostname differs from the referrer hostname, unless the &#39;alwaysSendReferrer&#39; field is set to true.</p>
     * Default Value: document.referrer<br>
     * Example Value: <code>http://example.com</code>
     **/
    public TrafficsourcesOptions documentReferrer(final String documentReferrer) {
        putText("referrer", documentReferrer);
        return this;
    }
}
