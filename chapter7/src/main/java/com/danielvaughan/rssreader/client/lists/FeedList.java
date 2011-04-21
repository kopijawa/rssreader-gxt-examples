package com.danielvaughan.rssreader.client.lists;

import java.util.Arrays;
import java.util.List;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.client.services.FeedServiceAsync;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FeedList extends LayoutContainer {

	private final ListField<BeanModel> feedList = new ListField<BeanModel>();
	private ListLoader<ListLoadResult<BeanModel>> loader;

	public FeedList() {
		setLayout(new FitLayout());
	}

	private String getTemplate() {
		StringBuilder sb = new StringBuilder();
		sb.append("<tpl for=\".\">");
		sb.append("<div class='x-combo-list-item'><b>{title}</b> - {description}</div>");
		sb.append("</tpl>");
		return sb.toString();
	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
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

		loader = new BaseListLoader<ListLoadResult<BeanModel>>(proxy, reader);

		ListStore<BeanModel> feedStore = new ListStore<BeanModel>(loader);

		feedList.setStore(feedStore);
		feedList.setTemplate(getTemplate());
		feedList.addSelectionChangedListener(new SelectionChangedListener<BeanModel>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<BeanModel> se) {
				Feed feed = se.getSelectedItem().getBean();
				if (feed != null) {
					Dispatcher.forwardEvent(AppEvents.FeedSelected, feed);
				}
			}
		});

		loader.load();

		add(feedList);
	}

	public void reloadFeeds() {
		loader.load();
	}

	public void selectFeed(Feed feed) {
		BeanModelFactory beanModelFactory = BeanModelLookup.get().getFactory(
				feed.getClass());
		feedList.setSelection(Arrays.asList(beanModelFactory.createModel(feed)));
	}
}
