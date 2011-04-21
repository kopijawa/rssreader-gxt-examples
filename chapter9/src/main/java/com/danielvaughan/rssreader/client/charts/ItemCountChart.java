package com.danielvaughan.rssreader.client.charts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.client.services.FeedServiceAsync;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.extjs.gxt.charts.client.Chart;
import com.extjs.gxt.charts.client.model.ChartModel;
import com.extjs.gxt.charts.client.model.axis.XAxis;
import com.extjs.gxt.charts.client.model.axis.YAxis;
import com.extjs.gxt.charts.client.model.charts.HorizontalBarChart;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ItemCountChart extends LayoutContainer {

	private final Chart chart = new Chart("gxt/chart/open-flash-chart.swf");

	public ItemCountChart() {
		chart.setVisible(false);
		final FeedServiceAsync feedService = Registry
				.get(RSSReaderConstants.FEED_SERVICE);
		feedService.loadFeedList(true, new AsyncCallback<List<Feed>>() {
			@Override
			public void onFailure(Throwable caught) {
				Dispatcher.forwardEvent(AppEvents.Error, caught);
			}

			@Override
			public void onSuccess(List<Feed> feeds) {
				chart.setChartModel(createChartModelData(feeds));
				chart.setVisible(true);
			}
		});
	}

	private ChartModel createChartModelData(List<Feed> feeds) {
		ChartModel chartModel = new ChartModel("Items per Feed",
				"font-size:14px;color:#000000");
		chartModel.setBackgroundColour("#ffffff");

		HashMap<String, Integer> data = prepareData(feeds);

		YAxis yAxis = new YAxis();
		for (String key : data.keySet()) {
			yAxis.addLabels(key);
		}
		yAxis.setOffset(true);

		chartModel.setYAxis(yAxis);
		XAxis xAxis = new XAxis();
		xAxis.setRange(0, 50, 10);
		chartModel.setXAxis(xAxis);

		HorizontalBarChart chartConfig = new HorizontalBarChart();
		List<Number> reverseValues = new ArrayList<Number>(data.values());
		Collections.reverse(reverseValues);
		chartConfig.addValues(new ArrayList<Number>(reverseValues));
		chartModel.addChartConfig(chartConfig);

		return chartModel;
	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		setLayout(new FitLayout());
		chart.setBorders(true);
		add(chart);
	}

	private HashMap<String, Integer> prepareData(List<Feed> feeds) {
		HashMap<String, Integer> counts = new HashMap<String, Integer>();
		for (Feed feed : feeds) {
			String feedTitle = feed.getTitle();
			int itemCount = feed.getItems().size();
			counts.put(feedTitle, itemCount);
		}
		return counts;
	}
}
