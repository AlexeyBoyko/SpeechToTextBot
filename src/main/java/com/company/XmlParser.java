package com.company;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

public class XmlParser {
    public static String parseDocument(String document) throws ParserConfigurationException, IOException, SAXException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(document)));
            doc.getDocumentElement().normalize();
            Element recognitionResults = doc.getDocumentElement();
            System.out.println("Root element :" + recognitionResults.getNodeName());
            String success = recognitionResults.getAttribute("success");
            if (success.equals("1")) {
                Node firstVariant = doc.getElementsByTagName("variant").item(0);
                return firstVariant.getTextContent();
            } else {
                return "[ОШИБКА РАСПОЗНАВАНИЯ]";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "[ОШИБКА]";
    }
}
