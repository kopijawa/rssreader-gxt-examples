package com.danielvaughan.rssreader.client.components;

import com.danielvaughan.rssreader.client.grids.ItemGrid;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

public class RssMainPanel extends ContentPanel {
	public RssMainPanel() {
		setHeading("Main");
		setLayout(new FitLayout());
		add(new ItemGrid());
	}
}
