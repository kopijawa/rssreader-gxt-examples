package com.danielvaughan.rssreader.client.mvc.views;

import com.danielvaughan.rssreader.client.mvc.controllers.AppController;
import com.danielvaughan.rssreader.client.mvc.events.AppEvents;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class AppView extends View {

	private final ContentPanel mainPanel = new ContentPanel();
	private final Viewport viewport = new Viewport();

	public AppView(AppController appController) {
		super(appController);
	}

	@Override
	protected void handleEvent(AppEvent event) {
		EventType eventType = event.getType();
		if (eventType.equals(AppEvents.Init)) {
			onInit(event);
		} else if (eventType.equals(AppEvents.Error)) {
			onError(event);
		} else if (eventType.equals(AppEvents.UIReady)) {
			onUIReady(event);
		} else if (eventType.equals(AppEvents.NavPanelReady)) {
			onNavPanelReady(event);
		} else if (eventType.equals(AppEvents.FeedPanelReady)) {
			onFeedPanelReady(event);
		} else if (eventType.equals(AppEvents.ItemPanelReady)) {
			onItemPanelReady(event);
		} else if (eventType.equals(AppEvents.StatusToolbarReady)) {
			onStatusToolbarReady(event);
		}
	}

	private void onError(AppEvent event) {
	}

	private void onFeedPanelReady(AppEvent event) {
		RowData rowData = new RowData();
		rowData.setHeight(.5);
		Component component = event.getData();
		mainPanel.add(component, rowData);
	}

	private void onInit(AppEvent event) {
		final BorderLayout borderLayout = new BorderLayout();
		viewport.setLayout(borderLayout);
		HTML headerHtml = new HTML();
		headerHtml.setHTML("<h1>RSS Reader</h1>");
		BorderLayoutData northData = new BorderLayoutData(LayoutRegion.NORTH,
				20);
		northData.setCollapsible(false);
		northData.setSplit(false);
		viewport.add(headerHtml, northData);
		BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
		centerData.setCollapsible(false);
		RowLayout rowLayout = new RowLayout(Orientation.VERTICAL);
		mainPanel.setHeaderVisible(false);
		mainPanel.setLayout(rowLayout);
		viewport.add(mainPanel, centerData);
	}

	private void onItemPanelReady(AppEvent event) {
		RowData rowData = new RowData();
		rowData.setHeight(.5);
		Component component = event.getData();
		mainPanel.add(component, rowData);
	}

	private void onNavPanelReady(AppEvent event) {
		BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST,
				200, 150, 300);
		westData.setCollapsible(true);
		westData.setSplit(true);
		Component component = event.getData();
		viewport.add(component, westData);
	}

	private void onStatusToolbarReady(AppEvent event) {
		Component component = event.getData();
		mainPanel.setBottomComponent(component);
	}

	private void onUIReady(AppEvent event) {
		RootPanel.get().add(viewport);
	}
}
