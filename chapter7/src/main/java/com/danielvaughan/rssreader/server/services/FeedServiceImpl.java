package com.danielvaughan.rssreader.server.services;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import com.danielvaughan.rssreader.client.services.FeedService;
import com.danielvaughan.rssreader.server.utils.FilePersistence;
import com.danielvaughan.rssreader.server.utils.Persistence;
import com.danielvaughan.rssreader.shared.model.Category;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.danielvaughan.rssreader.shared.model.Item;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
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
		Feed loadResult = loadFeed(feedUrl, false);
		if (loadResult.getTitle() != null) {
			feeds.put(feedUrl, loadResult);
			persistence.saveFeedList(feeds.keySet());
		}
	}

	@Override
	public Feed createNewFeed() {
		UUID uuid = UUID.randomUUID();
		return new Feed(uuid.toString());
	}

	private PagingLoadResult<Item> getPagingLoadResult(List<Item> items,
			PagingLoadConfig config) {
		List<Item> pageItems = new ArrayList<Item>();
		int offset = config.getOffset();
		int limit = items.size();
		if (config.getLimit() > 0) {
			limit = Math.min(offset + config.getLimit(), limit);
		}
		for (int i = config.getOffset(); i < limit; i++) {
			pageItems.add(items.get(i));
		}
		return new BasePagingLoadResult<Item>(pageItems, offset, items.size());

	}

	@Override
	public List<ModelData> loadCategorisedItems(String feedUrl,
			Category category) {
		List<Item> items = loadItems(feedUrl);
		Map<String, List<Item>> categorisedItems = new HashMap<String, List<Item>>();
		for (Item item : items) {
			String itemCategoryStr = item.getCategory();
			if (itemCategoryStr == null) {
				itemCategoryStr = "Uncategorised";
			}
			List<Item> categoryItems = categorisedItems.get(itemCategoryStr);
			if (categoryItems == null) {
				categoryItems = new ArrayList<Item>();
			}
			categoryItems.add(item);
			categorisedItems.put(itemCategoryStr, categoryItems);
		}
		if (category == null) {
			List<ModelData> categoryList = new ArrayList<ModelData>();
			for (String key : categorisedItems.keySet()) {
				categoryList.add(new Category(key));
			}
			return categoryList;
		} else {
			return new ArrayList<ModelData>(categorisedItems.get(category
					.getTitle()));
		}
	}

	private Feed loadFeed(String feedUrl, boolean loadItems) {
		Feed feed = new Feed(feedUrl);
		try {
			SAXBuilder parser = new SAXBuilder();
			Document document = parser.build(new URL(feedUrl));
			Element eleRoot = document.getRootElement();
			Element eleChannel = eleRoot.getChild("channel");
			feed.setTitle(eleChannel.getChildText("title"));
			feed.setDescription(eleChannel.getChildText("description"));
			feed.setLink(eleChannel.getChildText("link"));
			Element eleImage = eleChannel.getChild("image");
			feed.setImageUrl("");
			if (eleImage != null) {
				Element eleUrl = eleImage.getChild("url");
				if (eleUrl != null) {
					feed.setImageUrl(eleUrl.getText());
				}
			}
			if (loadItems) {
				feed.setItems(loadItems(feedUrl));
			}
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
	public List<Feed> loadFeedList(boolean loadItems) {
		feeds.clear();
		Set<String> feedUrls = persistence.loadFeedList();
		for (String feedUrl : feedUrls) {
			feeds.put(feedUrl, loadFeed(feedUrl, loadItems));
		}
		return new ArrayList<Feed>(feeds.values());
	}

	@Override
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
				Namespace ns = Namespace.getNamespace("media",
						"http://search.yahoo.com/mrss/");
				Element eleThumbnail = eleItem.getChild("thumbnail", ns);
				if (eleThumbnail != null) {
					item.setThumbnailUrl(eleThumbnail.getAttributeValue("url"));
				}
				String pubDateStr = eleItem.getChildText("pubDate");
				if (pubDateStr != null) {
					try {
						DateFormat df = new SimpleDateFormat(
								"EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z");
						item.setPubDate(df.parse(pubDateStr));
					} catch (ParseException e) {
						item.setPubDate(null);
					}
				}
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

	@Override
	public PagingLoadResult<Item> loadItems(String feedUrl,
			PagingLoadConfig config) {
		List<Item> items = loadItems(feedUrl);
		return getPagingLoadResult(items, config);
	}

	@Override
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
