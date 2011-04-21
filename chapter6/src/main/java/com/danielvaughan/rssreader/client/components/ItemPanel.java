package com.danielvaughan.rssreader.client.components;

import com.danielvaughan.rssreader.shared.model.Item;
import com.extjs.gxt.ui.client.core.Template;
import com.extjs.gxt.ui.client.util.Util;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;

public class ItemPanel extends ContentPanel {
	private final HTML html = new HTML();

	public void displayItem(Item item) {
		setHeading(item.getTitle());
		Template template = new Template(getTemplate());
		html.setHTML(template.applyTemplate(Util.getJsObject(item, 1)));
	}

	private String getTemplate() {
		StringBuilder sb = new StringBuilder();
		sb.append("<h1>{title}</h1>");
		sb.append("<p><i>{pubDate}</i></p>");
		sb.append("<hr/>");
		sb.append("<img src=\"{thumbnailUrl}\"/>");
		sb.append("<p>{description}</p>");
		return sb.toString();
	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		setHeading("Item");
		setLayout(new FitLayout());
		html.setStylePrimaryName("item");
		add(html);
	}
}
