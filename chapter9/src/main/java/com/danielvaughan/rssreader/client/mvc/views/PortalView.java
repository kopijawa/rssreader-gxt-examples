package com.danielvaughan.rssreader.client.mvc.views;

import com.danielvaughan.rssreader.client.RSSReaderConstants;
import com.danielvaughan.rssreader.client.mvc.controllers.PortalController;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.ui.RootPanel;

public class PortalView extends View {

	private final Portal portal = new Portal(2);

	public PortalView(PortalController portalController) {
		super(portalController);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		EventType eventType = event.getType();
		if (eventType.equals(AppEvents.NewPortletCreated)) {
			onNewPortletCreated(event);
		}
	}

	@Override
	protected void initialize() {
		portal.setColumnWidth(0, 0.3);
		portal.setColumnWidth(1, 0.7);
		final Viewport viewport = new Viewport();
		viewport.setLayout(new FitLayout());
		viewport.add(portal);
		RootPanel.get().add(viewport);
	}

	private void onNewPortletCreated(AppEvent event) {
		final Portlet portlet = (Portlet) event.getData();
		if (portlet.getId() == RSSReaderConstants.NAV_PORTLET
				|| portlet.getId() == RSSReaderConstants.CHART_PORTLET) {
			portal.add(portlet, 0);
		} else {
			portal.add(portlet, 1);
		}
	}
}