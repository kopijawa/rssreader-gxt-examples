package com.danielvaughan.rssreader.client.services;

import java.util.List;

import com.danielvaughan.rssreader.shared.model.Feed;
import com.danielvaughan.rssreader.shared.model.Item;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("feed-service")
public interface FeedService extends RemoteService {
	void addExistingFeed(String feedUrl);

	Feed createNewFeed();

	List<Feed> loadFeedList();

	List<Item> loadItems(String feedUrl);

	void saveFeed(Feed feed);
}
