package com.danielvaughan.rssreader.client.mvc.views;

import com.danielvaughan.rssreader.client.components.FeedPanel;
import com.danielvaughan.rssreader.client.grids.ItemGrid;
import com.danielvaughan.rssreader.client.mvc.controllers.FeedController;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TabPanelEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.TabItem;

public class FeedView extends View {
	private final FeedPanel feedPanel = new FeedPanel();

	public FeedView(FeedController feedController) {
		super(feedController);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		EventType eventType = event.getType();
		if (eventType.equals(AppEvents.Init)) {
			onInit(event);
		} else if (eventType.equals(AppEvents.FeedSelected)) {
			onFeedSelected(event);
		}
	}

	private void onFeedSelected(AppEvent event) {
		final Feed feed = event.getData();
		final ItemGrid itemGrid = new ItemGrid(feed);
		TabItem tabItem = new TabItem(feed.getTitle());
		tabItem.setId(feed.getUuid());
		tabItem.setData("feed", feed);
		tabItem.add(itemGrid);
		tabItem.addListener(Events.Select, new Listener<TabPanelEvent>() {
			@Override
			public void handleEvent(TabPanelEvent be) {
				itemGrid.resetSelection();
				Dispatcher.forwardEvent(new AppEvent(AppEvents.TabSelected,
						feed));
			}
		});
		tabItem.setClosable(true);
		feedPanel.addTab(tabItem);
	}

	private void onInit(AppEvent event) {
		Dispatcher.forwardEvent(new AppEvent(AppEvents.FeedPanelReady,
				feedPanel));
	}
}
