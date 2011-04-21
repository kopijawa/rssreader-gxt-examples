package com.danielvaughan.rssreader.client.components;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;

public class RssNavigationPanel extends ContentPanel 
{
	public RssNavigationPanel() 
	{
		setHeading("Navigation");
		final ToggleButton btnLinkFeed = new ToggleButton("Link feed");
		btnLinkFeed.setIconStyle("link-feed");
		setButtonAlign(HorizontalAlignment.LEFT);
		
		ToolTipConfig linkFeedToolTipConfig = new ToolTipConfig();
		linkFeedToolTipConfig.setTitle("Link to existing RSS feed");
		linkFeedToolTipConfig
				.setText("Allows you to enter the URL of an existing RSS feed you would like to link to");
		btnLinkFeed.setToolTip(linkFeedToolTipConfig);
		
		final LinkFeedPopup linkFeedPopup = new LinkFeedPopup();
		linkFeedPopup.setConstrainViewport(true);
		btnLinkFeed.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (btnLinkFeed.isPressed()) {
					linkFeedPopup.show(btnLinkFeed.getElement(), "bl-tl?");
				} else {
					linkFeedPopup.hide();
				}
			}
		});

		
		addButton(btnLinkFeed);
	}
}
