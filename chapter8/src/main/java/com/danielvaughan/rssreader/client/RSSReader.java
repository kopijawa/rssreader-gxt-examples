package com.danielvaughan.rssreader.client;

import com.danielvaughan.rssreader.client.mvc.controllers.PortalController;
import com.danielvaughan.rssreader.client.portlets.FeedPortlet;
import com.danielvaughan.rssreader.client.portlets.ItemPortlet;
import com.danielvaughan.rssreader.client.portlets.NavPortlet;
import com.danielvaughan.rssreader.client.services.FeedService;
import com.danielvaughan.rssreader.client.services.FeedServiceAsync;
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
	@Override
	public void onModuleLoad() {
		final FeedServiceAsync feedService = GWT.create(FeedService.class);
		Registry.register(RSSReaderConstants.FEED_SERVICE, feedService);
		Dispatcher dispatcher = Dispatcher.get();
		dispatcher.addController(new PortalController());
		new NavPortlet();
		// new OverviewPortlet();
		new FeedPortlet();
		new ItemPortlet();
	}
}
