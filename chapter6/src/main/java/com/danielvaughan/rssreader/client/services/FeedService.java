package com.danielvaughan.rssreader.client.services;

import java.util.List;

import com.danielvaughan.rssreader.shared.model.Category;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.danielvaughan.rssreader.shared.model.Item;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("feed-service")
public interface FeedService extends RemoteService {
	void addExistingFeed(String feedUrl);

	Feed createNewFeed();

	List<ModelData> loadCategorisedItems(String feedUrl, Category category);

	List<Feed> loadFeedList(boolean loadItems);

	List<Item> loadItems(String feedUrl);

	PagingLoadResult<Item> loadItems(String feedUrl,
			final PagingLoadConfig config);

	void saveFeed(Feed feed);
}
