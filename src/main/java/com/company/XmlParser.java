package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class XmlParser {
    public static String parseYandexResponse(String document) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = dBuilder.parse(new InputSource(new StringReader(document)));
        doc.getDocumentElement().normalize(); // удаляем лишние пробелы между тэгами и т.д.
        Element recognitionResults = doc.getDocumentElement();
        // проверяем признак успешного распознавания
        if (recognitionResults.getAttribute("success").equals("1")) {
            // выдаём самый первый вариант (видимо, наиболее вероятный)
            return doc.getElementsByTagName("variant").item(0).getTextContent();
        } else {
            return "***НЕ УДАЛОСЬ РАСПОЗНАТЬ***";
        }
    }
}
