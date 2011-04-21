package com.danielvaughan.rssreader.client.charts;

import java.util.HashMap;
import java.util.List;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.client.services.FeedServiceAsync;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.danielvaughan.rssreader.shared.model.Item;
import com.extjs.gxt.charts.client.Chart;
import com.extjs.gxt.charts.client.model.ChartModel;
import com.extjs.gxt.charts.client.model.charts.PieChart;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FeedChart extends LayoutContainer {

	private final Chart chart = new Chart("gxt/chart/open-flash-chart.swf");

	private ChartModel createChartModelData(List<Item> items) {
		ChartModel chartModel = new ChartModel("Posts per week of day",
				"font-size: 14px; font-family: Verdana; text-align: center;");
		chartModel.setBackgroundColour("#ffffff");
		PieChart pie = new PieChart();
		pie.setColours("#FF0000", "#FFA500", "#FFFF00", "#008000", "#0000FF",
				"#4B0082", "#EE82EE");
		HashMap<String, Integer> days = prepareData(items);
		for (String key : days.keySet()) {
			pie.addSlices(new PieChart.Slice(days.get(key), key));
		}
		chartModel.addChartConfig(pie);
		return chartModel;
	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		setLayout(new FitLayout());
		chart.setBorders(true);
		chart.setVisible(false);
		add(chart);
	}

	private HashMap<String, Integer> prepareData(List<Item> items) {
		HashMap<String, Integer> days = new HashMap<String, Integer>();
		for (Item item : items) {
			DateTimeFormat fmt = DateTimeFormat.getFormat("EEEE");
			String day = fmt.format(item.getPubDate());
			Integer dayOccurance = days.get(day);
			if (dayOccurance == null) {
				days.put(day, 1);
			} else {
				days.put(day, ++dayOccurance);
			}
		}
		return days;
	}

	public void setFeed(final Feed feed) {
		final FeedServiceAsync feedService = Registry
				.get(RSSReaderConstants.FEED_SERVICE);
		feedService.loadItems(feed.getUuid(), new AsyncCallback<List<Item>>() {
			@Override
			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}

			@Override
			public void onSuccess(List<Item> items) {
				chart.setChartModel(createChartModelData(items));
			}
		});
		if (!chart.isVisible()) {
			chart.setVisible(true);
		}
	}
}
