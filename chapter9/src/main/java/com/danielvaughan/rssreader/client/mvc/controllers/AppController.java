package com.danielvaughan.rssreader.client.mvc.controllers;

import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.client.mvc.views.AppView;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;

public class AppController extends Controller {

	private View appView;

	public AppController() {
		registerEventTypes(AppEvents.Init);
		registerEventTypes(AppEvents.Error);
		registerEventTypes(AppEvents.UIReady);
		registerEventTypes(AppEvents.NavPanelReady);
		registerEventTypes(AppEvents.FeedPanelReady);
		registerEventTypes(AppEvents.ItemPanelReady);
		registerEventTypes(AppEvents.StatusToolbarReady);
	}

	@Override
	public void handleEvent(AppEvent event) {
		forwardToView(appView, event);
	}

	@Override
	public void initialize() {
		super.initialize();
		appView = new AppView(this);
	}
}
