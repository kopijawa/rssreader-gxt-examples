package com.danielvaughan.rssreader.client.mvc.controllers;

import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.client.mvc.views.ItemView;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

public class ItemController extends Controller {

	private ItemView itemView;

	public ItemController() {
		registerEventTypes(AppEvents.Init);
		registerEventTypes(AppEvents.ItemSelected);
	}

	@Override
	public void handleEvent(AppEvent event) {
		forwardToView(itemView, event);
	}

	@Override
	public void initialize() {
		super.initialize();
		itemView = new ItemView(this);
	}
}
