package com.danielvaughan.rssreader.client.grids;

import java.util.ArrayList;
import java.util.List;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.client.services.FeedServiceAsync;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.danielvaughan.rssreader.shared.model.Item;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.core.XTemplate;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.RowExpander;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ItemGrid extends LayoutContainer {

	private final Feed feed;
	private Grid<ModelData> grid;

	public ItemGrid(Feed feed) {
		setLayout(new FitLayout());
		this.feed = feed;
	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		final List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		GridCellRenderer<ModelData> itemsRenderer = new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<ModelData> store, Grid<ModelData> grid) {
				String title = model.get("title");
				String description = model.get("description");
				return "<b>" + title + "</b><br/>" + description;
			}
		};
		ColumnConfig column = new ColumnConfig();
		column.setId("items");
		column.setRenderer(itemsRenderer);
		column.setHeader("Items");
		XTemplate xTemplate = XTemplate
				.create("<img class=\"left\" src=\"{thumbnailUrl}\"height=\"49px\"/><p>{description}</p>");
		RowExpander rowExpander = new RowExpander();
		rowExpander.setTemplate(xTemplate);
		columns.add(rowExpander);
		columns.add(column);
		final ColumnModel columnModel = new ColumnModel(columns);
		final FeedServiceAsync feedService = Registry
				.get(RSSReaderConstants.FEED_SERVICE);
		RpcProxy<List<Item>> proxy = new RpcProxy<List<Item>>() {
			@Override
			protected void load(Object loadConfig,
					AsyncCallback<List<Item>> callback) {
				feedService.loadItems(feed.getUuid(), callback);
			}
		};
		ListLoader<ListLoadResult<Item>> loader = new BaseListLoader<ListLoadResult<Item>>(
				proxy);
		ListStore<ModelData> itemStore = new ListStore<ModelData>(loader);

		grid = new Grid<ModelData>(itemStore, columnModel);
		grid.setBorders(true);
		grid.setAutoExpandColumn("items");
		grid.addPlugin(rowExpander);
		grid.getSelectionModel().addListener(Events.SelectionChange,
				new Listener<SelectionChangedEvent<Item>>() {
					@Override
					public void handleEvent(SelectionChangedEvent<Item> be) {
						Item item = be.getSelectedItem();
						Dispatcher.forwardEvent(AppEvents.ItemSelected, item);
					}
				});
		loader.load();
		add(grid);
	}

	public void resetSelection() {
		grid.getSelectionModel().deselectAll();
	}
}
