package com.danielvaughan.firstapp.client;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FirstGXTApp implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";


	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	private final Dialog dialogBox = new Dialog();
	private final VerticalPanel dialogVPanel = new VerticalPanel();
	private final TextField<String> nameField = new TextField<String>();
	private final Button sendButton = new Button("Send");
	private final HTML serverResponseLabel = new HTML();
	private final Label textToServerLabel = new Label();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		nameField.setValue("GWT User");

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);

		// Focus the cursor on the name field when the app loads
		nameField.focus();
		nameField.selectAll();

		// Create the popup dialog box

		dialogBox.setHeading("Remote Procedure Call");
		dialogBox.setAnimCollapse(true);

		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlign(HorizontalAlignment.RIGHT);
		dialogBox.setButtons(Dialog.CLOSE);
		dialogBox.add(dialogVPanel);

		Button closeButton = dialogBox.getButtonById(Dialog.CLOSE);
		// Add a handler to close the DialogBox
		closeButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.focus();
			}
		});

		sendButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			@Override
			public void componentSelected(ButtonEvent ce) {
				sendNameToServer();
			}
		});

		// Add a handler to send the name to the server
		nameField.addKeyListener(new KeyListener() {
			/**
			 * Fired when the user types in the nameField.
			 */
			@Override
			public void componentKeyUp(ComponentEvent event) {
				if (event.isSpecialKey(KeyCodes.KEY_ENTER)) {
					sendNameToServer();
				}
			}
		});
	}

	/**
	 * Send the name from the nameField to the server and wait for a response.
	 */
	private void sendNameToServer() {
		sendButton.setEnabled(false);
		String textToServer = nameField.getValue();
		textToServerLabel.setText(textToServer);
		serverResponseLabel.setText("");
		greetingService.greetServer(textToServer, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				dialogBox.setHeading("Remote Procedure Call - Failure");
				serverResponseLabel.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(SERVER_ERROR);
				dialogBox.show();
				dialogBox.center();
				Button closeButton = dialogBox.getButtonById(Dialog.CLOSE);
				closeButton.focus();
			}

			public void onSuccess(String result) {
				dialogBox.setHeading("Remote Procedure Call");
				serverResponseLabel.removeStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(result);
				dialogBox.show();
				dialogBox.center();
				Button closeButton = dialogBox.getButtonById(Dialog.CLOSE);
				closeButton.focus();
			}
		});
	}
}
