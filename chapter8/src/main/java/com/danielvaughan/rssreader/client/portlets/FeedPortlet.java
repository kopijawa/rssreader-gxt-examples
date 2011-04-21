package com.danielvaughan.rssreader.client.portlets;

import java.util.List;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.components.FeedPanel;
import com.danielvaughan.rssreader.client.grids.ItemGrid;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.dnd.DND;
import com.extjs.gxt.ui.client.dnd.DropTarget;
import com.extjs.gxt.ui.client.event.DNDEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TabPanelEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;

public class FeedPortlet extends Portlet {

	private final FeedPanel feedPanel = new FeedPanel();

	public FeedPortlet() {
		setHeading("Feed");
		setLayout(new FitLayout());
		setHeight(350);
		setId(RSSReaderConstants.FEED_PORTLET);
		feedPanel.setHeaderVisible(false);
		add(feedPanel);
		Dispatcher.forwardEvent(AppEvents.NewPortletCreated, this);
	}

	private void onFeedsDropped(DNDEvent event) {
		List<BeanModel> beanModels = event.getData();
		for (BeanModel beanModel : beanModels) {
			Feed feed = beanModel.getBean();
			final ItemGrid itemGrid = new ItemGrid(feed);
			TabItem tabItem = new TabItem(feed.getTitle());
			tabItem.setId(feed.getUuid());
			tabItem.setData("feed", feed);
			tabItem.add(itemGrid);
			tabItem.addListener(Events.Select, new Listener<TabPanelEvent>() {
				@Override
				public void handleEvent(TabPanelEvent be) {
					itemGrid.resetSelection();
				}
			});
			tabItem.setClosable(true);
			feedPanel.addTab(tabItem);
		}
	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		DropTarget target = new DropTarget(this) {
			@Override
			protected void onDragDrop(DNDEvent event) {
				super.onDragDrop(event);
				onFeedsDropped(event);
			}
		};
		target.setOperation(DND.Operation.COPY);
		target.setGroup(RSSReaderConstants.FEED_DD_GROUP);
	}
}
