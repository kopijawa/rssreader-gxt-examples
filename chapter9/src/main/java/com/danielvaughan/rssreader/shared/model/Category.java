package com.danielvaughan.rssreader.shared.model;

import com.extjs.gxt.ui.client.data.BaseTreeModel;

@SuppressWarnings("serial")
public class Category extends BaseTreeModel {
	private static int ID = 0;

	public Category() {
		set("id", ID++);
	}

	public Category(String title) {
		set("id", ID++);
		set("title", title);
	}

	public Integer getId() {
		return (Integer) get("id");
	}

	public String getTitle() {
		return (String) get("title");
	}
}
