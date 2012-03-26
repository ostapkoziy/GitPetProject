package com.epam.lab.dom;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.epam.lab.model.Deposit;

public class DomLauncher {
	private static final Logger LOG = Logger.getLogger(DomLauncher.class);
	public List<Deposit> parse(File file){
		DomHandler domHandler = new DomHandler();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		Document doc = null;
		DocumentBuilder dBuilder;
		try {
			dBuilder = factory.newDocumentBuilder();
			doc = dBuilder.parse(file.getPath());
		} catch (ParserConfigurationException e) {
			LOG.error("DOM Parser Config Error!",e);
		} catch (IOException e) {
			LOG.error("IO Exception occured",e);
		} catch (SAXException e) {
			LOG.error("Sax exception while DOM parsing!",e);
		}
		List<Deposit> arg = domHandler.parseDocument(file);
		for(Deposit i : arg){
			i.setFileName(file.getPath());
			i.setLastModified(file.lastModified());
		}
		return arg;
		
	}
}
