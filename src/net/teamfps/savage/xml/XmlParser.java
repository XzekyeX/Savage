package net.teamfps.savage.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser {
	private static final Pattern DATA = Pattern.compile(">(.+?)<");
	private static final Pattern START_TAG = Pattern.compile("<(.+?)>");
	private static final Pattern ATTR_NAME = Pattern.compile("(.+?)=");
	private static final Pattern ATTR_VAL = Pattern.compile("\"(.+?)\"");
	private static final Pattern CLOSED = Pattern.compile("(</|/>)");
//https://github.com/TheThinMatrix/OpenGL-Animation/blob/master/ColladaParser/xmlParser/XmlParser.java
	public static XmlNode loadXmlFile(String file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(file)));
			String line = "";
			while ((line = br.readLine()) != null) {
				// if(line.startsWith("</"))
				String[] sp = getStartTag(line).split(" ");
				XmlNode node = new XmlNode(sp[0].replace("/", ""));
				addAttributes(sp, node);
				addData(line, node);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void addData(String line, XmlNode node) {
		Matcher m = DATA.matcher(line);
		if (m.find()) node.setData(m.group(1));
	}

	private static void addAttribute(String str, XmlNode n) {
		Matcher m = ATTR_NAME.matcher(str);
		m.find();
		Matcher v = ATTR_VAL.matcher(str);
		v.find();
		n.addAttribute(m.group(1), v.group(1));
	}

	private static void addAttributes(String[] str, XmlNode n) {
		for (int i = 1; i < str.length; i++) {
			if (str[i].contains("=")) {
				addAttribute(str[i], n);
			}
		}
	}

	private static String getStartTag(String line) {
		Matcher match = START_TAG.matcher(line);
		match.find();
		return match.group(1);
	}
}
