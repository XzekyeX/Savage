package net.teamfps.savage.xml;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author Zekye
 *
 */
public class XmlNode {
	protected String name, data;
	protected HashMap<String, String> attributes = new HashMap<String, String>();
	protected HashMap<String, List<XmlNode>> childNodes = new HashMap<String, List<XmlNode>>();

	protected XmlNode(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getData() {
		return data;
	}

	public String getAttribute(String attr) {
		if (attributes.containsKey(attr)) return attributes.get(attr);
		return null;
	}

	public List<XmlNode> getChildren(String name) {
		if (childNodes.containsKey(name)) return childNodes.get(name);
		return null;
	}

	public XmlNode getChild(String name, int index) {
		List<XmlNode> nodes = getChildren(name);
		if (nodes != null && index <= nodes.size() - 1) {
			return nodes.get(index);
		}
		return null;
	}

	public XmlNode getChildWithAttribute(String name, String attr, String value) {
		List<XmlNode> children = getChildren(name);
		for (XmlNode n : children) {
			String v = n.getAttribute(attr);
			if (value.equals(v)) return n;
		}
		return null;
	}

	protected void addAttribute(String attr, String value) {
		attributes.put(attr, value);
	}

	protected void addChild(String key, List<XmlNode> value) {
		childNodes.put(key, value);
	}

	protected void addChild(XmlNode n) {
		List<XmlNode> list = getChildren(n.getName());
		if (list != null) addChild(n.getName(), list);
		list.add(n);
	}

	protected void setData(String data) {
		this.data = data;
	}
}
