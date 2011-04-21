package com.danielvaughan.rssreader.client.mvc.controllers;

import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.client.mvc.views.StatusView;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

public class StatusController extends Controller {

	private StatusView statusView;

	public StatusController() {
		registerEventTypes(AppEvents.Init);
		registerEventTypes(AppEvents.Error);
		registerEventTypes(AppEvents.UIReady);
		registerEventTypes(AppEvents.FeedSelected);
		registerEventTypes(AppEvents.ItemSelected);
	}

	@Override
	public void handleEvent(AppEvent event) {
		forwardToView(statusView, event);
	}

	@Override
	public void initialize() {
		super.initialize();
		statusView = new StatusView(this);
	}
}
