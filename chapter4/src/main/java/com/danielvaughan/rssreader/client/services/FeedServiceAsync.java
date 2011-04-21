package com.danielvaughan.rssreader.client.services;

import java.util.List;

import com.danielvaughan.rssreader.shared.model.Feed;
import com.danielvaughan.rssreader.shared.model.Item;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FeedServiceAsync {
	void addExistingFeed(String feedUrl, AsyncCallback<Void> callback);

	void createNewFeed(AsyncCallback<Feed> callback);

	void loadFeedList(AsyncCallback<List<Feed>> callback);

	void loadItems(String feedUrl, AsyncCallback<List<Item>> callback);

	void saveFeed(Feed feed, AsyncCallback<Void> callback);
}
