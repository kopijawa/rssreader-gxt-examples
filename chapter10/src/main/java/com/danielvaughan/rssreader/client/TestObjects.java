package com.danielvaughan.rssreader.client;

import java.util.Date;

import com.danielvaughan.rssreader.shared.model.Item;

public class TestObjects {
	public static Item getTestItem() {
		Item testItem = new Item();
		testItem.setTitle("Computers get more powerful");
		testItem.setDescription("New computers are more powerful than the computers that were around a year ago. They are also much more powerful than the computers from five years ago. If you were to compare current computers with the computers of twenty years ago you would fine they are far more powerful.");
		testItem.setLink("http://www.example.com/item573.html");
		testItem.setPubDate(new Date());
		testItem.setCategory("Category");
		testItem.setThumbnailUrl("http://www.danielvaughan.com/gxt-book/examples/images/computers.jpg");
		return testItem;
	}
}