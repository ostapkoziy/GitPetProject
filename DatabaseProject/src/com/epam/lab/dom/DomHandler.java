package com.epam.lab.dom;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomHandler {

	public void printXML(Node node) {
		String text = node.getNodeValue();
	   
		if (text != null && !text.isEmpty()) {
            System.out.println(text);
        }
		NamedNodeMap attributes = node.getAttributes();
		Node nameAttrib = attributes.getNamedItem("client");
		String name = nameAttrib.getTextContent();
		System.out.println("OLOLO - "+ name);
      
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
        	printXML(children.item(i));
        }
	}
}
