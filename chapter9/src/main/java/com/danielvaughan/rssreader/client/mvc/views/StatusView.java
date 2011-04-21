package com.danielvaughan.rssreader.client.mvc.views;

import com.danielvaughan.rssreader.client.mvc.controllers.StatusController;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.danielvaughan.rssreader.shared.model.Item;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class StatusView extends View {

	private final Status status = new Status();
	private final ToolBar toolBar = new ToolBar();

	public StatusView(StatusController statusController) {
		super(statusController);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		EventType eventType = event.getType();
		if (eventType.equals(AppEvents.Init)) {
			onInit();
			setStatus("Init");
		} else if (eventType.equals(AppEvents.FeedSelected)) {
			Feed feed = event.getData();
			setStatus("Feed Selected - (" + feed.getTitle() + ")");
		} else if (eventType.equals(AppEvents.ItemSelected)) {
			Item item = event.getData();
			setStatus("Item Selected - (" + item.getTitle() + ")");
		}
	}

	private void onInit() {
		status.setWidth("100%");
		status.setBox(true);
		toolBar.add(status);
		Dispatcher.forwardEvent(new AppEvent(AppEvents.StatusToolbarReady,
				toolBar));
	}

	public void setStatus(String message) {
		status.setText(message);
	}
}
