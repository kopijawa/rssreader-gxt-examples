package com.danielvaughan.rssreader.client.lists;

import java.util.List;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Format;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ListView;
import com.google.gwt.user.client.Element;

public class FeedOverviewView extends LayoutContainer {

	private final ListStore<BeanModel> feedStore = new ListStore<BeanModel>();
	private ListView<BeanModel> listView = new ListView<BeanModel>();

	public void addFeeds(List<BeanModel> feeds) {
		feedStore.add(feeds);
	}

	private String getTemplate() {
		StringBuilder sb = new StringBuilder();
		sb.append("<tpl for=\".\">");
		sb.append("<div class=\"feed-box\">");
		sb.append("<h1>{title}</h1>");
		sb.append("<tpl if=\"imageUrl!=''\">");
		sb.append("<img class=\"feed-thumbnail\" src=\"{imageUrl}\"title=\"{shortTitle}\">");
		sb.append("</tpl>");
		sb.append("<p>{shortDescription}</p>");
		sb.append("<ul>");
		sb.append("<tpl for=\"items\">");
		sb.append("<tpl if=\"xindex &lt; 3\">");
		sb.append("<li>{title}</li>");
		sb.append("</tpl>");
		sb.append("</tpl>");
		sb.append("</ul>");
		sb.append("</div>");
		sb.append("</tpl>");
		return sb.toString();
	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		setScrollMode(Scroll.AUTOY);
		listView = new ListView<BeanModel>() {
			@Override
			protected BeanModel prepareData(BeanModel feed) {
				feed.set("shortTitle",
						Format.ellipse((String) feed.get("title"), 50));
				feed.set("shortDescription",
						Format.ellipse((String) feed.get("description"), 100));
				return feed;
			}
		};
		listView.setStore(feedStore);
		listView.setTemplate(getTemplate());
		listView.setItemSelector("div.feed-box");
		listView.getSelectionModel().addListener(Events.SelectionChange,
				new Listener<SelectionChangedEvent<BeanModel>>() {

					@Override
					public void handleEvent(SelectionChangedEvent<BeanModel> be) {
						BeanModel feed = be.getSelection().get(0);
						Info.display("Feed selected",
								(String) feed.get("title"));
					}
				});
		add(listView);
	}
}