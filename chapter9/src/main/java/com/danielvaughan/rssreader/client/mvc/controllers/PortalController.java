package com.danielvaughan.rssreader.client.mvc.controllers;

import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.client.mvc.views.PortalView;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.core.client.GWT;

public class PortalController extends Controller {

	private PortalView portalView;

	public PortalController() {
		registerEventTypes(AppEvents.NewPortletCreated);
		registerEventTypes(AppEvents.Error);
	}

	@Override
	public void handleEvent(AppEvent event) {
		EventType eventType = event.getType();
		if (eventType.equals(AppEvents.Error)) {
			GWT.log("Error", (Throwable) event.getData());
		} else {
			forwardToView(portalView, event);
		}
	}

	@Override
	public void initialize() {
		super.initialize();
		portalView = new PortalView(this);
	}

}
