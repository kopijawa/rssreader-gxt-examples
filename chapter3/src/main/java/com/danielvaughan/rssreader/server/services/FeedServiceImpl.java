package com.danielvaughan.rssreader.server.services;

import java.io.IOException;
import java.util.UUID;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.danielvaughan.rssreader.client.services.FeedService;
import com.danielvaughan.rssreader.shared.model.Feed;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class FeedServiceImpl extends RemoteServiceServlet implements
		FeedService {
	@Override
	public Feed createNewFeed() {
		UUID uuid = UUID.randomUUID();
		return new Feed(uuid.toString());
	}

	@Override
	public void saveFeed(Feed feed) {
		Element eleRoot = new Element("rss");
		eleRoot.setAttribute(new Attribute("version", "2.0"));

		// Create a document from the feed object
		Document document = new Document(eleRoot);

		Element eleChannel = new Element("channel");
		Element eleTitle = new Element("title");
		Element eleDescription = new Element("description");
		Element eleLink = new Element("link");

		eleTitle.setText(feed.getTitle());
		eleDescription.setText(feed.getDescription());
		eleLink.setText(feed.getLink());

		eleChannel.addContent(eleTitle);
		eleChannel.addContent(eleDescription);
		eleChannel.addContent(eleLink);

		eleRoot.addContent(eleChannel);

		try {
			XMLOutputter serializer = new XMLOutputter();
			Format prettyFormat = Format.getPrettyFormat();
			serializer.setFormat(prettyFormat);
			System.out
					.println("At this point we would serialize the feed "
							+ feed.getTitle()
							+ " to a file. For now we are just going to write it to the console.");
			serializer.output(document, System.out);
		} catch (IOException e) {
			System.out.println("Error saving feed");
		}
	}

}
