package com.danielvaughan.rssreader.server.utils;

import java.util.Set;

import org.jdom.Document;

public interface Persistence {

	public String getUrl(String uuid);

	public Set<String> loadFeedList();

	public void saveFeedList(Set<String> feedUrls);

	public void saveFeedXml(String uuid, Document document);
}
