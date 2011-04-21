package com.danielvaughan.rssreader.client.mvc.controllers;

import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.client.mvc.views.FeedView;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

public class FeedController extends Controller {

	private FeedView feedView;

	public FeedController() {
		registerEventTypes(AppEvents.Init);
		registerEventTypes(AppEvents.FeedSelected);
	}

	@Override
	public void handleEvent(AppEvent event) {
		forwardToView(feedView, event);
	}

	@Override
	public void initialize() {
		super.initialize();
		feedView = new FeedView(this);
	}
}