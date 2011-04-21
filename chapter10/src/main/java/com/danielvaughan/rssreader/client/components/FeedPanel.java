package com.danielvaughan.rssreader.client.components;

import com.danielvaughan.rssreader.client.resources.Resources;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

public class FeedPanel extends ContentPanel {

	private final TabPanel tabPanel = new TabPanel();

	public FeedPanel() {
		setHeading("Main");
		setLayout(new FitLayout());
		add(tabPanel);
	}

	public void addTab(TabItem tabItem) {
		tabItem.setLayout(new FitLayout());
		tabItem.setIcon(Resources.ICONS.rss());
		tabItem.setScrollMode(Scroll.AUTO);
		String tabId = tabItem.getId();
		TabItem existingTab = tabPanel.findItem(tabId, false);
		if (existingTab == null) {
			tabPanel.add(tabItem);
			tabPanel.setSelection(tabItem);
		} else {
			tabPanel.setSelection(existingTab);
		}
	}
}
