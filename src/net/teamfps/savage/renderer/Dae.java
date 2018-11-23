package net.teamfps.savage.renderer;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Dae {
	private Mesh mesh;
	private String file;

	public Dae(String file) {
		this.file = file;
		load();
	}

	private void load() {
		Document d = readFile(file);
//		System.out.println("d: " + d.);
//		NodeList l = d.getElementsByTagName("source");
//		for (int i = 0; i < l.getLength(); i++) {
//			Node n = l.item(i);
//			System.out.println("Node: " + n.getNodeName());
//		}
//		System.out.println("l: " + l);
	}

	public Document readFile(String file) {
		try {
			File f = new File(file);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document d = db.parse(f);
			d.getDocumentElement().normalize();
			System.out.println("Root element: " + d.getDocumentElement().getNodeName());
			return d;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
