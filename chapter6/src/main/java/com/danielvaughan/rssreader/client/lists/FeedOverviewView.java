package com.danielvaughan.rssreader.client.lists;

import java.util.List;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.services.FeedServiceAsync;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Format;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ListView;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FeedOverviewView extends LayoutContainer {

	private ListView<BeanModel> listView = new ListView<BeanModel>();

	private String getTemplate() {
		StringBuilder sb = new StringBuilder();
		sb.append("<tpl for=\".\">");
		sb.append("<div class=\"feed-box\">");
		sb.append("<h1>{title}</h1>");
		sb.append("<tpl if=\"imageUrl!=''\">");
		sb.append("<img class=\"feed-thumbnail\" src=\"{imageUrl}\" title=\"{shortTitle}\">");
		sb.append("</tpl>");
		sb.append("<p>{shortDescription}</p>");
		sb.append("</div>");
		sb.append("</tpl>");
		return sb.toString();
	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		listView = new ListView<BeanModel>() {
			@Override
			protected BeanModel prepareData(BeanModel feed) {
				feed.set("shortTitle",
						Format.ellipse((String) feed.get("title"), 50));
				feed.set("shortDescription",
						Format.ellipse((String) feed.get("description"), 100));
				return feed;
			}
		};
		final FeedServiceAsync feedService = (FeedServiceAsync) Registry
				.get(RSSReaderConstants.FEED_SERVICE);
		RpcProxy<List<Feed>> proxy = new RpcProxy<List<Feed>>() {
			@Override
			protected void load(Object loadConfig,
					AsyncCallback<List<Feed>> callback) {
				feedService.loadFeedList(false, callback);
			}
		};
		BeanModelReader reader = new BeanModelReader();
		ListLoader<ListLoadResult<BeanModel>> loader = new BaseListLoader<ListLoadResult<BeanModel>>(
				proxy, reader);
		ListStore<BeanModel> feedStore = new ListStore<BeanModel>(loader);
		loader.load();
		listView.setStore(feedStore);
		listView.setTemplate(getTemplate());
		listView.setItemSelector("div.feed-box");
		listView.getSelectionModel().addListener(Events.SelectionChange,
				new Listener<SelectionChangedEvent<BeanModel>>() {
					@Override
					public void handleEvent(SelectionChangedEvent<BeanModel> be) {
						BeanModel feed = be.getSelection().get(0);
						Info.display("Feed selected",
								(String) feed.get("title"));
					}
				});
		add(listView);
	}
}
