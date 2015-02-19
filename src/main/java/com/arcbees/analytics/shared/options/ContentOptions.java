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

public class ContentOptions extends AnalyticsOptions {
    ContentOptions(OptionsCallback<?> optionsCallback) {
        super(optionsCallback);
    }

    /**
     * Optional. </p>
     * <p>
     * Specifies the hostname from which content was hosted.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>foo.com</code>
     **/
    public ContentOptions documentHostName(String documentHostName) {
        putText("hostname", documentHostName);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     * Specifies the full URL (excluding anchor) of the page. This field is initialized by the
     * create command.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>http://foo.com/home?a=b</code>
     **/
    public ContentOptions documentLocationUrl(String documentLocationUrl) {
        putText("location", documentLocationUrl);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     * The path portion of the page URL. Should begin with &#39;/&#39;. Used to specify virtual page
     * paths.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>/foo</code>
     **/
    public ContentOptions documentPath(String documentPath) {
        putText("page", documentPath.startsWith("/") ? documentPath : "/" + documentPath);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     * The title of the page / document. Defaults to document.title.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>Settings</code>
     **/
    public ContentOptions documentTitle(String documentTitle) {
        putText("title", documentTitle);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     * The ID of a clicked DOM element, used to disambiguate multiple links to the same URL in
     * In-Page Analytics reports when Enhanced Link Attribution is enabled for the property.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>nav_bar</code>
     **/
    public ContentOptions linkId(String linkId) {
        putText("linkid", linkId);
        return this;
    }

    /**
     * Optional. </p>
     * <p>
     * If not specified, this will default to the unique URL of the page by either using the &amp;dl
     * parameter as-is or assembling it from &amp;dh and &amp;dp. App tracking makes use of this for
     * the &#39;Screen Name&#39; of the screenview hit.
     * </p>
     * Default Value: None<br>
     * Example Value: <code>High Scores</code>
     **/
    public ContentOptions screenName(String screenName) {
        putText("screenName", screenName);
        return this;
    }
}
