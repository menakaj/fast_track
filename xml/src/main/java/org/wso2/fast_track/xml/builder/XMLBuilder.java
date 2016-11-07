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

package org.wso2.fast_track.xml.builder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

/**
 * This class generates an XML file for given data.
 *
 * The expected xml sample.
 *
 * <bookstore>
 *     <books>
 *         <book>
 *             <name>Book_A</name>
 *             <author>Author</author>
 *             <isbn>abcdef1234567</isbn>
 *         </book>
 *     </books>
 * </bookstore>
 *
 * The final result will be saved in bookstore.xml and as well as will be printed to the console.
 */
public class XMLBuilder {

    public XMLBuilder(){}

    public boolean buildXml() {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

            /**
             * Starts the root element.
             * <bookstore>
             *
             * </bookstore>
             * */
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("bookstore");
            doc.appendChild(root);

            /**
             * Creating books element.
             * */
            Element books_element = doc.createElement("books");
            root.appendChild(books_element);

            /**
             * Create individual book
             * */
            Element book = doc.createElement("book");
            books_element.appendChild(book);

            /**
             * Adding book properties
             * */
            Element title = doc.createElement("name");
            title.appendChild(doc.createTextNode("Book_A"));
            book.appendChild(title);

            Element author = doc.createElement("author");
            author.appendChild(doc.createTextNode("Author"));
            book.appendChild(author);

            Element isbn = doc.createElement("isbn");
            isbn.appendChild(doc.createTextNode("abcdefg123456"));
            book.appendChild(isbn);

            /**
             * Writes the content of the doc to a XML file.
             * */
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("bookstore.xml"));
            transformer.transform(source, result);

            /**
             * Writes the result to console. (System.out)
             * */
            StreamResult res = new StreamResult(new OutputStreamWriter(System.out));
//            transformer.transform(source, res);

            return true;
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            return false;
        } catch (TransformerException e) {
            e.printStackTrace();
            return false;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        }

    }

}
