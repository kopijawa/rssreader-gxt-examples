package com.danielvaughan.rssreader.client.mvc.views;

import com.danielvaughan.rssreader.client.components.NavPanel;
import com.danielvaughan.rssreader.client.mvc.controllers.NavController;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;

public class NavView extends View {
	private final NavPanel navPanel = new NavPanel();

	public NavView(NavController navController) {
		super(navController);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		EventType eventType = event.getType();
		if (eventType.equals(AppEvents.Init)) {
			Dispatcher.forwardEvent(new AppEvent(AppEvents.NavPanelReady,
					navPanel));
		} else if (eventType.equals(AppEvents.TabSelected)) {
			onTabSelected(event);
		} else if (eventType.equals(AppEvents.FeedAdded)) {
			onFeedAdded(event);
		}
	}

	private void onFeedAdded(AppEvent event) {
		navPanel.reloadFeeds();
	}

	private void onTabSelected(AppEvent event) {
		Feed feed = (Feed) event.getData();
		navPanel.selectFeed(feed);
	}

}