package com.danielvaughan.rssreader.client.components;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.lists.FeedList;
import com.danielvaughan.rssreader.client.services.FeedServiceAsync;
import com.danielvaughan.rssreader.client.windows.FeedWindow;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RssNavigationPanel extends ContentPanel {
	public RssNavigationPanel() {
		setHeading("Navigation");

		final Button btnCreateFeed = new Button("Create feed");
		btnCreateFeed.setIconStyle("create-feed");

		ToolTipConfig createNewToolTipConfig = new ToolTipConfig();
		createNewToolTipConfig.setTitle("Create a new RSS feed");
		createNewToolTipConfig.setText("Creates a new RSS feed");
		btnCreateFeed.setToolTip(createNewToolTipConfig);
		btnCreateFeed
				.addSelectionListener(new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						createNewFeedWindow();
					}
				});

		addButton(btnCreateFeed);

		final ToggleButton btnLinkFeed = new ToggleButton("Link feed");
		btnLinkFeed.setIconStyle("link-feed");
		setButtonAlign(HorizontalAlignment.LEFT);

		ToolTipConfig linkFeedToolTipConfig = new ToolTipConfig();
		linkFeedToolTipConfig.setTitle("Link to existing RSS feed");
		linkFeedToolTipConfig
				.setText("Allows you to enter the URL of an existing RSS feed you would like to link to");
		btnLinkFeed.setToolTip(linkFeedToolTipConfig);

		final LinkFeedPopup linkFeedPopup = new LinkFeedPopup();
		linkFeedPopup.setConstrainViewport(true);
		btnLinkFeed.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (btnLinkFeed.isPressed()) {
					linkFeedPopup.show(btnLinkFeed.getElement(), "bl-tl?");
				} else {
					linkFeedPopup.hide();
				}
			}
		});

		addButton(btnLinkFeed);
		setLayout(new FitLayout());
		add(new FeedList());
	}

	private void createNewFeedWindow() {
		final FeedServiceAsync feedService = Registry
				.get(RSSReaderConstants.FEED_SERVICE);
		feedService.createNewFeed(new AsyncCallback<Feed>() {
			@Override
			public void onFailure(Throwable caught) {
				Info.display("RSSReader", "Unable to create a new feed");
			}

			@Override
			public void onSuccess(Feed feed) {
				final Window newFeedWindow = new FeedWindow(feed);
				newFeedWindow.show();
			}
		});
	}
}
