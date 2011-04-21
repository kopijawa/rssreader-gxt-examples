package com.danielvaughan.rssreader.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Feed implements Serializable {

	private String description;
	private String link;
	private String title;
	private String uuid;
	private String imageUrl;
	private List<Item> items = new ArrayList<Item>();

	public Feed() {

	}

	public Feed(String uuid) {
		this.uuid = uuid;
	}

	public String getDescription() {
		return description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public List<Item> getItems() {
		return items;
	}

	public String getLink() {
		return link;
	}

	public String getTitle() {
		return title;
	}

	public String getUuid() {
		return uuid;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
