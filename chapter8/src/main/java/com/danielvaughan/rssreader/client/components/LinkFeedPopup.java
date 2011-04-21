package com.danielvaughan.rssreader.client.components;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.danielvaughan.rssreader.client.services.FeedServiceAsync;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Popup;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LinkFeedPopup extends Popup {
	private final TextField<String> tfUrl = new TextField<String>();

	public LinkFeedPopup() {
		setSize(300, 55);
		setBorders(true);
		setShadow(true);
		setAutoHide(false);
	}

	public void addFeed(final String feedUrl) {
		final FeedServiceAsync feedService = Registry
				.get(RSSReaderConstants.FEED_SERVICE);
		feedService.addExistingFeed(feedUrl, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Info.display("RSS Reader", "Failed to add feed at: " + feedUrl);
			}

			@Override
			public void onSuccess(Void result) {
				tfUrl.clear();
				Info.display("RSS Reader", "Feed at " + feedUrl
						+ " added successfully");
				Dispatcher.forwardEvent(AppEvents.FeedAdded);
				hide();
			}
		});
	}

	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		final Text txtExplaination = new Text("Enter a feed url");
		final Button btnAdd = new Button("add");
		btnAdd.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				addFeed(tfUrl.getValue());
			}
		});

		tfUrl.addKeyListener(new KeyListener() {
			@Override
			public void componentKeyDown(ComponentEvent event) {
				if (event.getKeyCode() == KeyCodes.KEY_ENTER) {
					addFeed(tfUrl.getValue());
				}
			}
		});
		tfUrl.setAllowBlank(false);
		tfUrl.setRegex("^http\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?$");
		tfUrl.setAllowBlank(false);
		tfUrl.getMessages().setBlankText(
				"Please enter the URL of an existing feed");
		tfUrl.setAutoValidate(true);
		tfUrl.getMessages()
				.setRegexText(
						"The link field must be a URL e.g. http://www.example.com/rss.xml");

		final BorderLayout layout = new BorderLayout();
		setLayout(layout);

		final BorderLayoutData northData = new BorderLayoutData(
				LayoutRegion.NORTH, 20);
		northData.setMargins(new Margins(2));
		add(txtExplaination, northData);

		final BorderLayoutData centerData = new BorderLayoutData(
				LayoutRegion.CENTER);
		centerData.setMargins(new Margins(2));
		add(tfUrl, centerData);

		final BorderLayoutData eastData = new BorderLayoutData(
				LayoutRegion.EAST, 50);
		eastData.setMargins(new Margins(2, 2, 2, 20));
		add(btnAdd, eastData);
	}

}
