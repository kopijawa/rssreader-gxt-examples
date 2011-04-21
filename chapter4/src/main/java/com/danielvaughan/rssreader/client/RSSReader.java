package com.danielvaughan.rssreader.client;

import com.danielvaughan.rssreader.client.components.RssMainPanel;
import com.danielvaughan.rssreader.client.components.RssNavigationPanel;
import com.danielvaughan.rssreader.client.services.FeedService;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RSSReader implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		Registry.register(RSSReaderConstants.FEED_SERVICE,
				GWT.create(FeedService.class));
		Registry.register(RSSReaderConstants.FEED_STORE,
				new ListStore<BeanModel>());
		Viewport viewport = new Viewport();
		final BorderLayout borderLayout = new BorderLayout();

		BorderLayoutData northData = new BorderLayoutData(LayoutRegion.NORTH,
				20);
		northData.setCollapsible(false);
		northData.setSplit(false);

		HTML headerHtml = new HTML();
		headerHtml.setHTML("<h1>RSS Reader</h1>");
		viewport.add(headerHtml, northData);

		BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
		centerData.setCollapsible(false);

		BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST,
				200, 150, 300);
		westData.setCollapsible(true);
		westData.setSplit(true);

		RssMainPanel mainPanel = new RssMainPanel();
		RssNavigationPanel navPanel = new RssNavigationPanel();
		viewport.add(mainPanel, centerData);
		viewport.add(navPanel, westData);

		viewport.setLayout(borderLayout);
		RootPanel.get().add(viewport);
	}
}
