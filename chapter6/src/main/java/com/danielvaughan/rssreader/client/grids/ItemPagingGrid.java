package com.danielvaughan.rssreader.client.grids;

import java.util.ArrayList;
import java.util.List;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.services.FeedServiceAsync;
import com.danielvaughan.rssreader.shared.model.Item;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ItemPagingGrid extends LayoutContainer {

	private static final int PAGE_SIZE = 10;

	public ItemPagingGrid() {
		setLayout(new FitLayout());
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
		columns.add(column);
		final ColumnModel columnModel = new ColumnModel(columns);
		final String TEST_DATA_FILE = "http://feeds.feedburner.com/extblog";
		final FeedServiceAsync feedService = Registry
				.get(RSSReaderConstants.FEED_SERVICE);
		RpcProxy<PagingLoadResult<Item>> proxy = new RpcProxy<PagingLoadResult<Item>>() {
			@Override
			protected void load(Object loadConfig,
					AsyncCallback<PagingLoadResult<Item>> callback) {
				feedService.loadItems(TEST_DATA_FILE,
						(PagingLoadConfig) loadConfig, callback);
			}
		};
		PagingLoader<PagingLoadResult<Item>> loader = new BasePagingLoader<PagingLoadResult<Item>>(
				proxy);
		ListStore<ModelData> itemStore = new ListStore<ModelData>(loader);
		final PagingToolBar toolBar = new PagingToolBar(PAGE_SIZE);
		toolBar.bind(loader);

		Grid<ModelData> grid = new Grid<ModelData>(itemStore, columnModel);
		grid.setBorders(true);
		grid.setAutoExpandColumn("items");
		loader.load();

		ContentPanel panel = new ContentPanel();
		panel.setLayout(new FitLayout());
		panel.add(grid);
		panel.setHeaderVisible(false);
		panel.setBottomComponent(toolBar);
		add(panel);
	}
}
