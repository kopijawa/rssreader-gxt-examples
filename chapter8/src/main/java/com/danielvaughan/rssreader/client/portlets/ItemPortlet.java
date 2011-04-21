package com.danielvaughan.rssreader.client.portlets;

import java.util.List;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.components.ItemPanel;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.shared.model.Item;
import com.extjs.gxt.ui.client.dnd.DND;
import com.extjs.gxt.ui.client.dnd.DropTarget;
import com.extjs.gxt.ui.client.event.DNDEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

public class ItemPortlet extends Portlet {

	public ItemPortlet() {
		setHeading("Item");
		setLayout(new FitLayout());
		setHeight(250);
		setId(RSSReaderConstants.ITEM_PORTLET);
		final ItemPanel itemPanel = new ItemPanel();
		itemPanel.setHeaderVisible(false);
		add(itemPanel);
		Dispatcher.forwardEvent(AppEvents.NewPortletCreated, this);
		DropTarget target = new DropTarget(this) {
			@Override
			protected void onDragDrop(DNDEvent event) {
				super.onDragDrop(event);
				List<Item> items = event.getData();
				itemPanel.displayItem(items.get(0));
			}
		};
		target.setOperation(DND.Operation.COPY);
		target.setGroup(RSSReaderConstants.ITEM_DD_GROUP);
	}
}
