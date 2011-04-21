package com.danielvaughan.rssreader.server.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class FilePersistence implements Persistence {

	private final static Logger LOGGER = Logger.getLogger(FilePersistence.class
			.getName());

	private String dataFolder;
	private String feedFilePath;
	private String baseUrl;

	private final Properties properties = new Properties();

	public FilePersistence() {
		// Read properties file.
		try {
			ClassLoader loader = ClassLoader.getSystemClassLoader();
			InputStream in = loader.getResourceAsStream("rssreader.properties");
			properties.load(in);

			dataFolder = (String) properties.get("data.folder");
			String feedFile = (String) properties.get("feed.file");
			feedFilePath = dataFolder + "\\" + feedFile;
			baseUrl = (String) properties.get("base.url");

			initDataFolder();
		} catch (IOException e) {
			LOGGER.severe("Unable to load properties file");
		}
	}

	private String generateFilePath(String feedId) {
		return dataFolder + "/" + feedId + ".xml";
	}

	@Override
	public String getUrl(String feedId) {
		String fileName = generateFilePath(feedId);
		return baseUrl + "/" + fileName;
	}

	private void initDataFolder() {
		try {
			File dataFolderFile = new File(dataFolder);
			if (!dataFolderFile.exists()) {
				dataFolderFile.mkdir();
			}
			File feedFile = new File(feedFilePath);
			if (!feedFile.exists()) {
				feedFile.createNewFile();
			}
		} catch (IOException e) {
			LOGGER.severe("Error initialising data folder");
		}
	}

	@Override
	public Set<String> loadFeedList() {
		Set<String> feedUrls = new HashSet<String>();
		try {
			FileInputStream fstream = new FileInputStream(feedFilePath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				feedUrls.add(strLine);
			}
			in.close();
			return feedUrls;
		} catch (Exception e) {
			LOGGER.severe("Error: " + e.getMessage());
			return feedUrls;
		}
	}

	@Override
	public void saveFeedList(Set<String> feedUrls) {
		try {
			FileWriter fileWriter = new FileWriter(feedFilePath);
			BufferedWriter out = new BufferedWriter(fileWriter);
			for (String feedUrl : feedUrls) {
				String line = feedUrl.concat("\n");
				out.write(line);
			}
			out.close();
		} catch (Exception e) {
			LOGGER.severe("Error: " + e.getMessage());
		}
	}

	@Override
	public void saveFeedXml(String feedId, Document document) {
		try {
			File file = new File(generateFilePath(feedId));
			XMLOutputter serializer = new XMLOutputter();
			Format prettyFormat = Format.getPrettyFormat();
			serializer.setFormat(prettyFormat);
			FileWriter writer = new FileWriter(file);
			serializer.output(document, writer);
			writer.close();
		} catch (IOException e) {
			LOGGER.severe("Error: " + e.getMessage());
		}
	}
}
