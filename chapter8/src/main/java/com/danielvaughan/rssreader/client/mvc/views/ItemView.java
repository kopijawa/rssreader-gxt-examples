package com.danielvaughan.rssreader.client.mvc.views;

import com.danielvaughan.rssreader.client.components.ItemPanel;
import com.danielvaughan.rssreader.client.mvc.controllers.ItemController;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.shared.model.Item;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;

public class ItemView extends View {
	private final ItemPanel itemPanel = new ItemPanel();

	public ItemView(ItemController itemController) {
		super(itemController);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		EventType eventType = event.getType();
		if (eventType.equals(AppEvents.Init)) {
			Dispatcher.forwardEvent(new AppEvent(AppEvents.ItemPanelReady,
					itemPanel));
		} else if (eventType.equals(AppEvents.ItemSelected)) {
			onItemSelected(event);
		}
	}

	private void onItemSelected(AppEvent event) {
		Item item = (Item) event.getData();
		itemPanel.displayItem(item);
	}
}