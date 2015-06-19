#Universal Analytics
A universal analytics implementation and service provider.

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
analytics.enablePlugin(AnalyticsPlugin.DISPLAY); // (optional) Provides demographics information.
analytics.sendPageView().go(); // (recommended) track the initial pageview
```

##Mobile Install

When running in mobile applications under `file:` protocol, there is no cookie for UA to store
the data, thus there are some aditional steps on mobile:
1. Disable cookies storage
2. Set a clientId

[More details here](https://developers.google.com/analytics/devguides/collection/analyticsjs/domains#disableCookies)

It can be done as follows:

```
createOpts = analytics.create(trackerCode);
createOpts.clientId('client_id').storage(Storage.NONE).go();
```

##Basic Usage

Inject Analytics into the class you want to track events from.

**REMEMBER TO CALL GO()**

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

##Mobile Usage

When dealing with mobile analytics the [ScreenView
events should be used instead of PageView](https://developers.google.com/analytics/devguides/collection/analyticsjs/screens).
It's also mandatory to send `AppName` otherwise the realtime analytics won't work.
Another useful information to send is the ScreenName.

e.g.:
```
analytics.sendScreenView().screenName('current_page_name')
.appTrackingOptions()
.applicationName('my_app_name').go();
```

##Server Side

```
install(new ServerAnalyticsModule("UA-XXXXXX-X"));
```

You can use analytics on the server exactly the same as you do on the client.

Server side calls work via the [Measurement Protocol](https://developers.google.com/analytics/devguides/collection/protocol/v1/)

A filter will automatically fill out the Measurement Protocol required fields for you from the _ga cookie or create the cookie if it doesn't exist.

`setGlobalOptions()` and `enablePlugin()` have no effect on server calls.

If you're using multiple trackers then you should call `create().trackerName("My Tracker").go()` once at the beginning of each request to create your secondary trackers before calling any analytics methods.  All other options sent to create() on the server will be ignored.

If you're setting a custom cookie name on the client then the automatic filter will not work correctly because it assumes that the cookie name is : _ga.  Raise an issue on this project if you need to set the cookie name for some reason.

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

##Credits
Thanks to Richard Wallis (https://github.com/rdwallis) for his initial pull request against GWTP (https://github.com/ArcBees/GWTP/pull/521)
See his repo here https://github.com/rdwallis/gwt-universal-analytics

##Thanks to
[![Arcbees.com](http://i.imgur.com/HDf1qfq.png)](http://arcbees.com)

[![Atlassian](http://i.imgur.com/BKkj8Rg.png)](https://www.atlassian.com/)

[![IntelliJ](https://lh6.googleusercontent.com/--QIIJfKrjSk/UJJ6X-UohII/AAAAAAAAAVM/cOW7EjnH778/s800/banner_IDEA.png)](http://www.jetbrains.com/idea/index.html)
