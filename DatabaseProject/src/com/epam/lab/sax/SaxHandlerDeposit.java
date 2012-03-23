package com.epam.lab.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.epam.lab.model.Client;
import com.epam.lab.model.Deposit;

public class SaxHandlerDeposit extends DefaultHandler {
	private Deposit deposit = null;
	private boolean isCash = false;
	private boolean isPercent = false;
	private boolean isUser = false;
	private boolean isDeposit = false;
	private boolean isDeposit2 = false;

	@Override
	public void startDocument() throws SAXException {
		deposit = new Deposit();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		isDeposit = qName.equals("Deposit");
		isCash = qName.equals("cash");
		isPercent = qName.equals("percent");
		isUser = qName.equals("client");

		if (isDeposit) {
			this.deposit = new Deposit();
			isDeposit2 = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (isDeposit2) {
			//add parsed Client to list
			SaxLauncher.deposits.add(deposit);
			isDeposit2 = false;
		}
		isCash = false;
		isPercent = false;
		isUser = false;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (isCash) {
			isCash = false;
			deposit.setCash(new Integer(new String(ch, start, length)));
		}
		if (isPercent) {
			isPercent = false;
			deposit.setPercent(new Double(new String(ch, start, length)));
		}
		if (isUser) {
			isUser = false;
			deposit.setClient(new String(ch, start, length));
		}
	}

	@Override
	public void endDocument() throws SAXException {
	}
}
