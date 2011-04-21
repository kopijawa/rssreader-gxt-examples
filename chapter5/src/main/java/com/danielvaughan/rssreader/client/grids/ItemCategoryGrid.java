package com.danielvaughan.rssreader.client.grids;

import java.util.Arrays;
import java.util.List;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.resources.Resources;
import com.danielvaughan.rssreader.client.services.FeedServiceAsync;
import com.danielvaughan.rssreader.shared.model.Category;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGrid;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGridCellRenderer;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ItemCategoryGrid extends LayoutContainer {
	public ItemCategoryGrid() {
		setLayout(new FitLayout());
	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		final FeedServiceAsync feedService = (FeedServiceAsync) Registry
				.get(RSSReaderConstants.FEED_SERVICE);
		final String TEST_DATA_FILE = "http://feeds.feedburner.com/extblog";
		RpcProxy<List<ModelData>> proxy = new RpcProxy<List<ModelData>>() {
			@Override
			protected void load(Object loadConfig,
					AsyncCallback<List<ModelData>> callback) {
				feedService.loadCategorisedItems(TEST_DATA_FILE,
						(Category) loadConfig, callback);
			}
		};

		final TreeLoader<ModelData> loader = new BaseTreeLoader<ModelData>(
				proxy) {
			@Override
			public boolean hasChildren(ModelData parent) {
				if (parent instanceof Category) {
					return true;
				} else {
					return false;
				}
			}
		};

		final TreeStore<ModelData> feedStore = new TreeStore<ModelData>(loader);
		ColumnConfig title = new ColumnConfig("title", "Title", 200);
		title.setRenderer(new TreeGridCellRenderer<ModelData>());
		ColumnModel columnModel = new ColumnModel(Arrays.asList(title));
		TreeGrid<ModelData> treeGrid = new TreeGrid<ModelData>(feedStore,
				columnModel);
		treeGrid.setBorders(true);
		treeGrid.setAutoExpandColumn("title");
		treeGrid.getStyle().setLeafIcon(Resources.ICONS.rss());
		loader.load();
		add(treeGrid);
	}
}
