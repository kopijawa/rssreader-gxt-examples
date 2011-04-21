package com.danielvaughan.rssreader.client.portlets;

import java.util.List;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.charts.FeedChart;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.dnd.DND;
import com.extjs.gxt.ui.client.dnd.DropTarget;
import com.extjs.gxt.ui.client.event.DNDEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;

public class ChartPortlet extends Portlet {
	private final FeedChart feedChart = new FeedChart();

	public ChartPortlet() {
		setHeading("Chart");
		setId(RSSReaderConstants.CHART_PORTLET);
		setLayout(new FitLayout());
		setHeight(250);
		add(feedChart);
		Dispatcher.forwardEvent(AppEvents.NewPortletCreated, this);
	}

	private void onFeedsDropped(DNDEvent event) {
		List<BeanModel> beanModels = event.getData();
		for (BeanModel beanModel : beanModels) {
			Feed feed = beanModel.getBean();
			feedChart.setFeed(feed);
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
