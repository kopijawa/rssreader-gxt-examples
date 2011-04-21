package com.danielvaughan.rssreader.client.mvc.controllers;

import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.client.mvc.views.NavView;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

public class NavController extends Controller {

	private NavView navView;

	public NavController() {
		registerEventTypes(AppEvents.Init);
		registerEventTypes(AppEvents.FeedAdded);
		registerEventTypes(AppEvents.TabSelected);
	}

	@Override
	public void handleEvent(AppEvent event) {
		forwardToView(navView, event);
	}

	@Override
	public void initialize() {
		super.initialize();
		navView = new NavView(this);
	}
}
