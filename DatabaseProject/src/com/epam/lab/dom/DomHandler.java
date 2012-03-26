package com.epam.lab.dom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.epam.lab.model.Deposit;

public class DomHandler {
	private final static Logger LOG = Logger.getLogger(DomHandler.class);
	private List<Deposit> deposits = new ArrayList<Deposit>();
	private Deposit deposit = new Deposit();

	public List<Deposit> parseDocument(File file) {
		try {
			File domFile = new File(file.getPath());
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(domFile);

			NodeList nList = doc.getElementsByTagName("Deposit");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				deposit = new Deposit();
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					deposit.setClient((getTagValue("client",
							eElement)));
					deposit.setCash(Integer.valueOf(getTagValue("cash",
							eElement)));
					deposit.setPercent(Double.valueOf(getTagValue("percent",
							eElement)));
					deposits.add(deposit);
				}
			}
		} catch (ParserConfigurationException e) {
			LOG.info("Parser configuration",e);
		} catch (SAXException e) {
			LOG.info("Sax exception occured",e);
		} catch (IOException e) {
			LOG.info(e);
		}
		return deposits;
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}
}
