/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * you may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.fast_track.xml.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

/**
 * Class to parse the given xml file.
 * */
public class XMLParser extends DefaultHandler {
    private static Writer out;
    StringBuffer textBuffer;

    static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String W3C_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    public static void main(String[] args) {
        DefaultHandler handler = new XMLParser();
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware(true);
        saxParserFactory.setValidating(true);

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        File xml = new File(classLoader.getResource("xml/sample.xml").getFile());

        try {
            out = new OutputStreamWriter(System.out, "UTF8");
            SAXParser parser = saxParserFactory.newSAXParser();
            parser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_SCHEMA);
            parser.setProperty(JAXP_SCHEMA_SOURCE, new File(classLoader.getResource("xsd/sample.xsd").getFile()));
            parser.parse(xml, handler);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the given string to the console.
     * @param s : String to be written.
     * @throws SAXException
     */
    private void emit(String s) throws SAXException {
        try {
            out.write(s);
            out.flush();
        } catch (IOException e) {
            throw new SAXException("I/O error", e);
        }
    }

    /**
     * Handles the line end.
     * @throws SAXException
     */
    private void nl() throws SAXException {
        String lineEnd = System.getProperty("line.separator");
        try {
            out.write(lineEnd);
        } catch (IOException e) {
            throw new SAXException("I/O Error", e);
        }
    }

    /**
     * Hanldes the start of an xml document.
     * @throws SAXException
     */
    public void startDocument() throws SAXException {
        emit("<?xml version='1.0' encoding='UTF-8'?>");
        nl();
    }

    /**
     * Handles end of an xml document.
     * @throws SAXException
     */
    public void endDocument() throws SAXException {
        nl();
        try {
            out.flush();
        } catch (IOException e) {
            throw new SAXException("I/O Error", e);
        }
    }

    /**
     * Hanldes the start of an XML element.
     * @param nameSpaceURI :
     * @param sName : Simple name.
     * @param qName : Qualified name.
     * @param attributes : XML attributes.
     * @throws SAXException
     */
    public void startElement(String nameSpaceURI, String sName, String qName, Attributes attributes)
            throws SAXException {
        echoText();
        String eName = sName;
        if ("".equals(eName)) {
            eName = qName;
        }
        emit("<");
        emit(eName);
        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                String aName = attributes.getLocalName(i);
                if ("".equals(aName)) {
                    aName = attributes.getQName(i);
                }
                emit("");
                emit(aName + "=\"" + attributes.getValue(i) + "\"");
            }
        }
        emit(">");
    }

    /**
     * Handles the end of an XML element.
     * @param nameSpaceURI
     * @param sName : Simple name.
     * @param qName : Qualified name.
     * @throws SAXException
     */
    public void endElement(String nameSpaceURI, String sName, String qName) throws SAXException {
        echoText();
        String eName = sName;
        if ("".equals(eName))
            eName = qName;
        emit("</" + eName + ">");
    }

    /**
     * Handles the characters which are delivered by the parser.
     * @param buff : Character buffer.
     * @param offset : Offset.
     * @param len : Length.
     * @throws SAXException
     */
    public void characters(char buff[], int offset, int len) throws SAXException {
        String s = new String(buff, offset, len);
        if (textBuffer == null) {
            textBuffer = new StringBuffer(s);
        } else {
            textBuffer.append(s);
        }
    }

    /**
     * Sends the buffer content to the writer.
     * @throws SAXException
     */
    private void echoText() throws SAXException {
        if (textBuffer == null) return;
        String s = "" + textBuffer;
        emit(s);
        textBuffer = null;
    }

}
