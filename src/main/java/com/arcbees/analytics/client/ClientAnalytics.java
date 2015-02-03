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

package com.arcbees.analytics.client;

import java.util.HashMap;
import java.util.Map;

import com.arcbees.analytics.shared.AnalyticsImpl;
import com.arcbees.analytics.shared.AnalyticsPlugin;
import com.arcbees.analytics.shared.HitType;
import com.arcbees.analytics.shared.ProtocolTranslator;
import com.arcbees.analytics.shared.options.AnalyticsOptions;
import com.arcbees.analytics.shared.options.CreateOptions;
import com.arcbees.analytics.shared.options.GeneralOptions;
import com.arcbees.analytics.shared.options.TimingOptions;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;

public class ClientAnalytics extends AnalyticsImpl {
    private final Map<String, Double> timingEvents = new HashMap<>();

    @Inject
    ClientAnalytics(@GaAccount final String userAccount,
            @AutoCreate final boolean autoCreate, 
            @TrackInitialPageView final boolean trackInitialPageView) {
        super(userAccount);

        init();

        if (autoCreate) {
            create().go();
            if (trackInitialPageView) {
            	sendPageView().go();
            }
        }
    }

    private void call(final JSONValue... params) {
        final JSONArray aryParams = new JSONArray();
        for (final JSONValue p : params) {
            aryParams.set(aryParams.size(), p);
        }
        nativeCall(aryParams.getJavaScriptObject());
    }

    @Override
    public CreateOptions create(final String userAccount) {
        return new AnalyticsOptions(new JSONOptionsCallback() {

            @Override
            public void onCallback(final JSONObject options) {
                call(new JSONString("create"), new JSONString(userAccount), options);
                setGlobalSettings().forceSsl(true).go();
            }
        }).createOptions();
    }

    @Override
    public void enablePlugin(final AnalyticsPlugin plugin) {
        if (plugin.getJsName() != null) {
            call(new JSONString("require"), new JSONString(plugin.getFieldName()), new JSONString(plugin.getJsName()));
        } else {
            call(new JSONString("require"), new JSONString(plugin.getFieldName()));
        }
    }

    @Override
    public TimingOptions endTimingEvent(final String trackerName, final String timingCategory,
            final String timingVariableName) {
        final String key = getTimingKey(timingCategory, timingVariableName);
        if (timingEvents.containsKey(key)) {
            return sendTiming(trackerName, timingCategory, timingVariableName,
                    (int) (Duration.currentTimeMillis() - timingEvents.remove(key)));
        }
        return new AnalyticsOptions(new JSONOptionsCallback() {

            @Override
            public void onCallback(final JSONObject options) {
                //Do nothing a timing event was ended before it was started.  This is here just to stop a crash.
            }
        }).timingOptions(timingCategory, timingVariableName, 0);
    }
    
    private void init() {
    	nativeInit();
    	ScriptInjector.fromUrl("//www.google-analytics.com/analytics.js")
    	.setWindow(ScriptInjector.TOP_WINDOW)
    	.setCallback(new Callback<Void, Exception>() {
			
			@Override
			public void onSuccess(Void result) {
			
			}
			
			@Override
			public void onFailure(Exception reason) {
				loadFallback();
			}
		}).inject();
    }
    
    private boolean firstRun = true;
    
    public void fallback(String commandName, String command, String options) {
    	
    	
    	if ("send".equals(commandName)) {
    		JSONObject jsonOptions = JSONParser.parseStrict(options).isObject();
    		StringBuilder url = new StringBuilder();
    		url.append("/collect?");
    		url.append(ProtocolTranslator.getFieldName("hitType")).append("=").append(URL.encodeQueryString(command));
    		
    		for (String key: jsonOptions.keySet()) {
    			if (!"hitCallback".equals(key)) {
	    			JSONValue jsonValue = jsonOptions.get(key);
	    			String strValue = "";
	    			if (jsonValue.isBoolean() != null) {
	    				strValue = jsonValue.isBoolean().booleanValue() + "";
	    			} else if (jsonValue.isNumber() != null) {
	    				strValue = jsonValue.isNumber().doubleValue() + "";
	    			} else if (jsonValue.isString() != null) {
	    				strValue = jsonValue.isString().stringValue();
	    			}
	    			url.append("&").append(ProtocolTranslator.getFieldName(key)).append("=").append(URL.encodeQueryString(strValue));
    			}
    		}
    		try {
				RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url.toString());
				requestBuilder.setCallback(new RequestCallback() {
					
					@Override
					public void onResponseReceived(Request request, Response response) {
						//TODO call hitcallback if needed.
						
					}
					
					@Override
					public void onError(Request request, Throwable exception) {
						// TODO Auto-generated method stub
						
					}
				});
				requestBuilder.send();
			} catch (RequestException e) {
			}
    		
    	}
    }
    
    private native void loadFallback() /*-{
    	var name = $wnd['GoogleAnalyticsObject'];
    	var preCalled = $wnd[name].q;
    	var that = this;
    	$wnd[name] = $entry(function() {
    		var commandName = arguments[0];
    		var command = arguments.length > 2 ? arguments[1]: null;
    		var options = arguments[arguments.length - 1];
    		
          	that.@com.arcbees.analytics.client.ClientAnalytics::fallback(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(commandName, command, JSON.stringify(options));
        });
    	for (var i =0; i < preCalled.length; i++) {
    		$wnd[name].apply(null, preCalled[i]);
    	}
    	
    }-*/;

    private native void nativeInit()/*-{
	    (function(wnd, name) {
	    wnd['GoogleAnalyticsObject'] = name;
	    wnd[name] = wnd[name] || function() {
	        (wnd[name].q = wnd[name].q || [])
	        .push(arguments)
	    }, wnd[name].l = 1 * new Date();
		})($wnd, '__ua');
    }-*/;

    private native void nativeCall(final JavaScriptObject params) /*-{
        $wnd.__ua.apply(null, params);
    }-*/;

    @Override
    public AnalyticsOptions send(final String trackerName, final HitType hitType) {
        return new AnalyticsOptions(new JSONOptionsCallback() {

            @Override
            public void onCallback(final JSONObject options) {
                call(new JSONString(trackerName == null ? "send" : trackerName + ".send"),
                        new JSONString(hitType.getFieldName()), options);
            }
        });
    }

    @Override
    public GeneralOptions setGlobalSettings() {
        return new AnalyticsOptions(new JSONOptionsCallback() {

            @Override
            public void onCallback(final JSONObject options) {
                call(new JSONString("set"), options);
            }

        }).generalOptions();
    }

    @Override
    public void startTimingEvent(final String timingCategory, final String timingVariableName) {
        timingEvents.put(getTimingKey(timingCategory, timingVariableName), Duration.currentTimeMillis());
    }
}
