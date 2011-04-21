package com.danielvaughan.rssreader.client.services;

import com.danielvaughan.rssreader.shared.model.Feed;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("feed-service")
public interface FeedService extends RemoteService {
	Feed createNewFeed();

	void saveFeed(Feed feed);
}
