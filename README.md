#Universal Analytics
A complete GWT wrapper for Google Universal Analytics.

Thanks to Richard Wallis (https://github.com/rdwallis) for his initial pull request against GWTP (https://github.com/ArcBees/GWTP/pull/521)
See his repo here https://github.com/rdwallis/gwt-universal-analytics

##Install

Add the following to your pom

```
<dependency>
	<groupId>com.arcbees.analytics</groupId>
	<artifactId>universal-analytics</artifactId>
	<version>2.1</version>
</dependency>
```

Add the following to your gwt.xml

```
<inherits name="com.arcbees.analytics.Analytics"/>
```

Then in your gin module:
```
install(new AnalyticsModule.Builder("UA-XXXXXXXX-X").build());
```

##Advanced Install

By default AnalyticsModule.Builder will automatically create a tracker for you.

If you want to set up your tracker manually (eg because you want to use plugins) call:

```
install(new AnalyticsModule.Builder("UA-XXXXXXXX-X").autoCreate(false).build());
```

Then in your entry point or bootstrapper(GWTP) call the following:

```
analytics.create().go();
analytics.enablePlugin(AnalyticsPlugin.DISPLAY); //Provides demographics information.
```

##Basic Usage

Inject Analytics into the class you want to track events from.

**REMEBER TO CALL GO()**

Analytics uses a version of the builder pattern where you call the type of tracking method you want, then chain the options you need to the call followed by go().

eg:
```
analytics.sendPageView().go(); //track a pageview
analytics.sendPageView().documentPath("/foo").go(); //track a pageview for page /foo
analytics.sendEvent("button", "click").eventLabel("my label").go(); //send event with label
```

If you want to change the global settings call setGlobalSettings in the same way. 
```
analytics.setGlobalSettings().anonymizeIp(true).go(); //anonymize ips
```

##Server Side



```
install(new ServerAnalyticsModule("UA-XXXXXX-X"));
```

You can use analytics on the server exactly the same as you do on the client.

Server side calls work via the [Measurement Protocol](https://developers.google.com/analytics/devguides/collection/protocol/v1/)

A filter will automatically fill out the Measurement Protocol required fields for you from the _ga cookie or create the cookie if it doesn't exist.

`setGlobalOptions()` and `enablePlugin()` have no effect on server calls.

If you're using multiple trackers then you should call `create().trackerName("My Tracker").go()` to create your tracker before making the tracker call.  All other options sent to create() on the server will be ignored.

If you're using SetCookieName() on the client then the automatic filter will not be able to keep the server and client in sync since it assumes that the cookie name is : _ga.  Raise an issue on this project if you need to set the cookie name for some reason.

##Timing Events

To start a timing event call
```
analytics.startTimingEvent("My Category", "My Timing Variable Name");
```
Then to end it call.

```
analytics.endTimingEvent("My Category", "My Timing Variable Name").userTimingLabel("My Label").go();
```

This will automatically create a timing event marking the difference between when the start and end calls are made.

The category and variable names for the start and end calls must be exactly the same, or the call will have no effect.

