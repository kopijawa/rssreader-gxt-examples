package com.danielvaughan.rssreader.server.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.jdom.Document;

import com.danielvaughan.rssreader.server.model.FeedUrl;

public class GaePersistence implements Persistence {
	@Override
	public String getUrl(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Set<String> loadFeedList() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Set<String> urls = new HashSet<String>();
			Query q = pm.newQuery("select url from " + FeedUrl.class.getName());
			List ids = (List) q.execute();
			urls.addAll(ids);
			return urls;
		} finally {
			pm.close();
		}
	}

	@Override
	public void saveFeedList(Set<String> feedUrls) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			for (String url : feedUrls) {
				FeedUrl feedUrl = new FeedUrl(url);
				pm.makePersistent(feedUrl);
			}
		} finally {
			pm.close();
		}
	}

	@Override
	public void saveFeedXml(String uuid, Document document) {
		// TODO Auto-generated method stub

	}
}
