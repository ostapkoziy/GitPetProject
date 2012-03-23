package com.epam.lab.sax;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.epam.lab.model.Client;
import com.epam.lab.model.Deposit;

public class SaxLauncher {
	private static final Logger LOG = Logger.getLogger(SaxLauncher.class);

	public static List<Client> clients = new ArrayList<Client>();
	public static List<Deposit> deposits = new ArrayList<Deposit>();

	public void parseClient(File file) {
		clients.clear();
		SAXParser parser = null;
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SaxHandler saxParser = new SaxHandler();
		try {
			parser = factory.newSAXParser();
			parser.parse(file.getPath(), saxParser);
		} catch (ParserConfigurationException e) {
			LOG.error("SAX Parser Config Error!", e);
		} catch (SAXException e) {
			LOG.error("Sax exception while SAX parsing!", e);
		} catch (IOException e) {
			LOG.error("IO Exception occured", e);
		}
		for(Client i : clients){
			i.setFileName(file.getPath());
			i.setLastModified(file.lastModified());
		}
	}
	public void parseDeposit(File file) {
		clients.clear();
		SAXParser parser = null;
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SaxHandlerDeposit saxParser = new SaxHandlerDeposit();
		try {
			parser = factory.newSAXParser();
			parser.parse(file.getPath(), saxParser);
		} catch (ParserConfigurationException e) {
			LOG.error("SAX Parser Config Error!", e);
		} catch (SAXException e) {
			LOG.error("Sax exception while SAX parsing!", e);
		} catch (IOException e) {
			LOG.error("IO Exception occured", e);
		}
		for(Deposit i : deposits){
			i.setFileName(file.getPath());
			i.setLastModified(file.lastModified());
		}
	}
}
