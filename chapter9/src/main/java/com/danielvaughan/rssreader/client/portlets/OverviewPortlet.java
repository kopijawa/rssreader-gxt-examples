package com.danielvaughan.rssreader.client.portlets;

import java.util.List;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.lists.FeedOverviewView;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.dnd.DND;
import com.extjs.gxt.ui.client.dnd.DropTarget;
import com.extjs.gxt.ui.client.event.DNDEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

public class OverviewPortlet extends Portlet {
	public OverviewPortlet() {
		setHeading("Overview");
		setLayout(new FitLayout());
		setHeight(250);
		setId(RSSReaderConstants.OVERVIEW_PORTLET);
		final FeedOverviewView feedOverviewView = new FeedOverviewView();
		add(feedOverviewView);
		DropTarget target = new DropTarget(this) {
			@Override
			protected void onDragDrop(DNDEvent event) {
				super.onDragDrop(event);
				List<BeanModel> beanModels = event.getData();
				feedOverviewView.addFeeds(beanModels);
			}
		};
		target.setOperation(DND.Operation.COPY);
		target.setGroup(RSSReaderConstants.FEED_DD_GROUP);
		Dispatcher.forwardEvent(AppEvents.NewPortletCreated, this);
	}
}