package com.danielvaughan.rssreader.server.services;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.danielvaughan.rssreader.client.services.FeedService;
import com.danielvaughan.rssreader.server.utils.FilePersistence;
import com.danielvaughan.rssreader.server.utils.Persistence;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.danielvaughan.rssreader.shared.model.Item;
import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class FeedServiceImpl extends RemoteServiceServlet implements
		FeedService {

	private final static Logger LOGGER = Logger.getLogger(FeedServiceImpl.class
			.getName());

	private Map<String, Feed> feeds = new HashMap<String, Feed>();

	private final Persistence persistence = new FilePersistence();

	@Override
	public void addExistingFeed(String feedUrl) {
		Feed loadResult = loadFeed(feedUrl);
		if (loadResult.getTitle() != null) {
			feeds.put(feedUrl, loadFeed(feedUrl));
			persistence.saveFeedList(feeds.keySet());
		}
	}

	@Override
	public Feed createNewFeed() {
		UUID uuid = UUID.randomUUID();
		return new Feed(uuid.toString());
	}

	private Feed loadFeed(String feedUrl) {
		Feed feed = new Feed(feedUrl);
		try {
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(new URL(feedUrl));
			Element eleRoot = document.getRootElement();
			Element eleChannel = eleRoot.getChild("channel");
			feed.setTitle(eleChannel.getChildText("title"));
			feed.setDescription(eleChannel.getChildText("description"));
			feed.setLink(eleChannel.getChildText("link"));
			return feed;
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "IO Error loading feed", e);
			return feed;
		} catch (JDOMException e) {
			LOGGER.log(Level.SEVERE, "Error parsing feed", e);
			return feed;
		}
	}

	@Override
	public List<Feed> loadFeedList() {
		feeds.clear();
		Set<String> feedUrls = persistence.loadFeedList();
		for (String feedUrl : feedUrls) {
			feeds.put(feedUrl, loadFeed(feedUrl));
		}
		return new ArrayList<Feed>(feeds.values());
	}

	@SuppressWarnings("unchecked")
	public List<Item> loadItems(String feedUrl) {
		List<Item> items = new ArrayList<Item>();
		try {
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(new URL(feedUrl));
			Element eleRoot = document.getRootElement();
			Element eleChannel = eleRoot.getChild("channel");
			List<Element> itemElements = eleChannel.getChildren("item");
			for (Element eleItem : itemElements) {
				Item item = new Item();
				item.setTitle(eleItem.getChildText("title"));
				item.setDescription(eleItem.getChildText("description"));
				item.setLink(eleItem.getChildText("link"));
				item.setCategory(eleItem.getChildText("category"));
				items.add(item);
			}
			return items;
		} catch (IOException e) {
			e.printStackTrace();
			return items;

		} catch (JDOMException e) {
			e.printStackTrace();
			return items;
		}
	}

	public void saveFeed(Feed feed) {
		Element eleRoot = new Element("rss");
		eleRoot.setAttribute(new Attribute("version", "2.0"));

		// Create a document from the feed object
		Document document = new Document(eleRoot);

		Element eleChannel = new Element("channel");
		Element eleTitle = new Element("title");
		Element eleDescription = new Element("description");
		Element eleLink = new Element("link");

		eleTitle.setText(feed.getTitle());
		eleDescription.setText(feed.getDescription());
		eleLink.setText(feed.getLink());

		eleChannel.addContent(eleTitle);
		eleChannel.addContent(eleDescription);
		eleChannel.addContent(eleLink);

		eleRoot.addContent(eleChannel);

		persistence.saveFeedXml(feed.getUuid(), document);
		addExistingFeed(persistence.getUrl(feed.getUuid()));
	}
}
