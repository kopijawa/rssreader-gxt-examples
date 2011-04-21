package com.danielvaughan.rssreader.client.mvc.events;

import com.extjs.gxt.ui.client.event.EventType;

public class AppEvents {
	public static final EventType Init = new EventType();
	public static final EventType Error = new EventType();
	public static final EventType UIReady = new EventType();
	public static final EventType NavPanelReady = new EventType();
	public static final EventType FeedPanelReady = new EventType();
	public static final EventType ItemPanelReady = new EventType();

	public static final EventType FeedSelected = new EventType();
	public static final EventType ItemSelected = new EventType();
	public static final EventType TabSelected = new EventType();
	public static final EventType FeedAdded = new EventType();
	public static final EventType StatusToolbarReady = new EventType();
}