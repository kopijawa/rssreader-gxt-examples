package com.danielvaughan.rssreader.shared.model;

import com.extjs.gxt.ui.client.data.BaseModel;

@SuppressWarnings("serial")
public class Item extends BaseModel  {
		
	public Item()
	{
		
	}
	
	public String getCategory() {
		return get("category");
	}

	public String getDescription() {
		return get("description");
	}
	
	public String getLink() {
		return get("link");
	}

	public String getTitle() {
		return get("title");
	}

	public void setCategory(String category) {
		set("category", category);
	}
	
	public void setDescription(String description) {
		set("description", description);
	}

	public void setLink(String link) {
		set("link", link);
	}

	public void setTitle(String title) {
		set("title", title);
	}
}
