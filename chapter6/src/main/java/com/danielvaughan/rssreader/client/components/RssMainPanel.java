package com.danielvaughan.rssreader.client.components;

import com.danielvaughan.rssreader.client.lists.FeedOverviewView;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class RssMainPanel extends ContentPanel {
	public RssMainPanel() {
		setHeading("Main");
		setLayout(new FitLayout());
		add(new FeedOverviewView());

		ToolBar toolBar = new ToolBar();
		Status status = new Status();
		status.setWidth(150);
		status.setBox(true);
		status.setText("OK");
		toolBar.add(status);
		setBottomComponent(toolBar);
	}
}
