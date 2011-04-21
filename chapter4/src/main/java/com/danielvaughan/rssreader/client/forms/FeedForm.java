package com.danielvaughan.rssreader.client.forms;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.services.FeedServiceAsync;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FeedForm extends FormPanel {

	private final TextField<String> tfTitle = new TextField<String>();
	private final TextArea taDescription = new TextArea();
	private final TextField<String> tfLink = new TextField<String>();

	public FeedForm() {
		setHeaderVisible(false);
	}

	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);

		tfTitle.setFieldLabel("Title");
		tfTitle.setAllowBlank(false);
		tfTitle.getMessages().setBlankText("Title is required");

		taDescription.setFieldLabel("Description");
		taDescription.setAllowBlank(false);
		taDescription.getMessages().setBlankText("Description is required");

		tfLink.setFieldLabel("Link");
		tfLink.setAllowBlank(false);
		tfLink.setRegex("^http\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?$");
		tfLink.getMessages().setBlankText("Link is required");
		tfLink.getMessages()
				.setRegexText(
						"The link field must be a URL e.g. http://www.example.com/rss.xml");

		add(tfTitle);
		add(taDescription);
		add(tfLink);
	}

	public void save(final Feed feed) {
		feed.setTitle(tfTitle.getValue());
		feed.setDescription(taDescription.getValue());
		feed.setLink(tfLink.getValue());

		final FeedServiceAsync feedService = Registry
				.get(RSSReaderConstants.FEED_SERVICE);
		feedService.saveFeed(feed, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Info.display("RSS Reader",
						"Failed to save feed: " + feed.getTitle());
			}

			@Override
			public void onSuccess(Void result) {
				Info.display("RSS Reader", "Feed " + feed.getTitle()
						+ " saved sucessfully");
				final ListStore<BeanModel> feedStore = Registry
						.get(RSSReaderConstants.FEED_STORE);
				BeanModelFactory beanModelFactory = BeanModelLookup.get()
						.getFactory(feed.getClass());
				feedStore.add(beanModelFactory.createModel(feed));
			}
		});

	}

}
