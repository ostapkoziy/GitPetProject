package com.epam.lab.sax;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.epam.lab.model.Bank;
import com.epam.lab.model.Client;

public class SaxHandler extends DefaultHandler {
	private Client client = null;
	private boolean isName = false;
	private boolean isAdress = false;
	private boolean isPhone = false;
	private boolean isClient = false;
	private boolean isClient2 = false;

	@Override
	public void startDocument() throws SAXException {
		client = new Client();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		isClient = qName.equals("Client");
		isName = qName.equals("name");
		isAdress = qName.equals("adress");
		isPhone = qName.equals("phoneNumber");

		if (isClient) {
			this.client = new Client();
			isClient2 = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (isClient2) {
			//add parsed Client to list
			SaxLauncher.clients.add(client);
			isClient2 = false;
		}
		isName = false;
		isAdress = false;
		isPhone = false;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (isName) {
			isName = false;
			client.setName(new String(ch, start, length));
		}
		if (isPhone) {
			isPhone = false;
			client.setPhoneNumber(new String(ch, start, length));
		}
		if (isAdress) {
			isAdress = false;
			client.setAdress(new String(ch, start, length));
		}
	}

	@Override
	public void endDocument() throws SAXException {
	}

}
