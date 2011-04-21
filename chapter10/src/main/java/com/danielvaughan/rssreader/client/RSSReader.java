package com.danielvaughan.rssreader.client;

import com.danielvaughan.rssreader.client.mvc.controllers.AppController;
import com.danielvaughan.rssreader.client.mvc.controllers.FeedController;
import com.danielvaughan.rssreader.client.mvc.controllers.ItemController;
import com.danielvaughan.rssreader.client.mvc.controllers.NavController;
import com.danielvaughan.rssreader.client.mvc.controllers.StatusController;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.client.services.FeedService;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RSSReader implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Registry.register(RSSReaderConstants.FEED_SERVICE, GWT.
				create(FeedService.class));
		Dispatcher dispatcher = Dispatcher.get();
		dispatcher.addController(new AppController());
		dispatcher.addController(new NavController());
		dispatcher.addController(new FeedController());
		dispatcher.addController(new ItemController());
		dispatcher.addController(new StatusController());
		dispatcher.dispatch(AppEvents.Init);
		dispatcher.dispatch(AppEvents.UIReady);
	}
}
