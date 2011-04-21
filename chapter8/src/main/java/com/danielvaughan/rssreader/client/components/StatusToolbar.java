package com.danielvaughan.rssreader.client.components;

import com.extjs.gxt.ui.client.widget.Composite;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class StatusToolbar extends Composite {

	private final Status status = new Status();

	public StatusToolbar() {
		ToolBar toolBar = new ToolBar();
		status.setWidth("100%");
		status.setBox(true);
		toolBar.add(status);
		initComponent(toolBar);
	}

	public void setStatus(String message) {
		status.setText(message);
	}
}
