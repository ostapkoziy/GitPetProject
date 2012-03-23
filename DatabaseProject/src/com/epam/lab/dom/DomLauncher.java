package com.epam.lab.dom;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DomLauncher {
	private static final Logger LOG = Logger.getLogger(DomLauncher.class);
	public void parse(){
		DomHandler domHandler = new DomHandler();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		Document doc = null;
		DocumentBuilder dBuilder;
		try {
			dBuilder = factory.newDocumentBuilder();
			doc = dBuilder.parse("resources\\clients.xml");
		} catch (ParserConfigurationException e) {
			LOG.error("DOM Parser Config Error!",e);
		} catch (IOException e) {
			LOG.error("IO Exception occured",e);
		} catch (SAXException e) {
			LOG.error("Sax exception while DOM parsing!",e);
		}
		domHandler.printXML(doc.getFirstChild());
	}
}
