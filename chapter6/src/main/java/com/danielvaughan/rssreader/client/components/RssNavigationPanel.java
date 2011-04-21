package com.danielvaughan.rssreader.client.components;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.lists.FeedList;
import com.danielvaughan.rssreader.client.services.FeedServiceAsync;
import com.danielvaughan.rssreader.client.windows.FeedWindow;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RssNavigationPanel extends ContentPanel {
	public RssNavigationPanel() {
		setHeading("Navigation");
		setLayout(new FitLayout());
		initToolbar();
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

	private void initToolbar() {
		final ToolBar toolbar = new ToolBar();
		final Button btnAddFeed = new Button("Add feed");
		btnAddFeed.setIconStyle("create-feed");
		ToolTipConfig addFeedToolTipConfig = new ToolTipConfig();
		addFeedToolTipConfig.setTitle("Add a new RSS feed");
		addFeedToolTipConfig.setText("Adds a new RSS feed");
		btnAddFeed.setToolTip(addFeedToolTipConfig);
		Menu menu = new Menu();
		final MenuItem miCreateFeed = new MenuItem("Create feed");
		miCreateFeed.setIconStyle("create-feed");
		ToolTipConfig createNewToolTipConfig = new ToolTipConfig();
		createNewToolTipConfig.setTitle("Create a new RSS feed");
		createNewToolTipConfig.setText("Creates a new RSS feed");
		miCreateFeed.setToolTip(createNewToolTipConfig);
		miCreateFeed.addSelectionListener(new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent me) {
				createNewFeedWindow();
			}
		});
		menu.add(miCreateFeed);
		final MenuItem miLinkFeed = new MenuItem("Link feed");
		miLinkFeed.setIconStyle("link-feed");
		ToolTipConfig linkFeedToolTipConfig = new ToolTipConfig();
		linkFeedToolTipConfig.setTitle("Link to existing RSS feed");
		linkFeedToolTipConfig
				.setText("Allows you to enter the URL of an existing RSS feed you would like to link to");
		miLinkFeed.setToolTip(linkFeedToolTipConfig);
		final LinkFeedPopup addFeedPopup = new LinkFeedPopup();
		addFeedPopup.setConstrainViewport(true);
		miLinkFeed.addSelectionListener(new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent me) {
				addFeedPopup.show(miLinkFeed.getElement(), "tl-bl?");
			}
		});
		menu.add(miLinkFeed);
		btnAddFeed.setMenu(menu);
		toolbar.add(btnAddFeed);
		setTopComponent(toolbar);
	}
}
