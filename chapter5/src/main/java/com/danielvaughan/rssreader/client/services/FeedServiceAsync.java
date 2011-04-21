package com.danielvaughan.rssreader.client.services;

import java.util.List;

import com.danielvaughan.rssreader.shared.model.Category;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.danielvaughan.rssreader.shared.model.Item;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FeedServiceAsync {
	void addExistingFeed(String feedUrl, AsyncCallback<Void> callback);

	void createNewFeed(AsyncCallback<Feed> callback);

	void loadCategorisedItems(String feedUrl, Category category,
			AsyncCallback<List<ModelData>> callback);

	void loadFeedList(AsyncCallback<List<Feed>> callback);

	void loadItems(String feedUrl, AsyncCallback<List<Item>> callback);

	void loadItems(String feedUrl, PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<Item>> callback);

	void saveFeed(Feed feed, AsyncCallback<Void> callback);
}
