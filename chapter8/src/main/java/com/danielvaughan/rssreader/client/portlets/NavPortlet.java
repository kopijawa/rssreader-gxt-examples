package com.danielvaughan.rssreader.client.portlets;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.components.NavPanel;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

public class NavPortlet extends Portlet {

	public NavPortlet() {
		setHeading("Navigation");
		setLayout(new FitLayout());
		setHeight(610);
		setId(RSSReaderConstants.NAV_PORTLET);
		NavPanel navPanel = new NavPanel();
		navPanel.setHeaderVisible(false);
		add(navPanel);
		Dispatcher.forwardEvent(AppEvents.NewPortletCreated, this);
	}

}
