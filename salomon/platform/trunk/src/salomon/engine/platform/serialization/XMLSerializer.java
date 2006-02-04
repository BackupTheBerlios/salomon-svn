/*
 * Copyright (C) 2004 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.platform.serialization;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import salomon.util.serialization.SimpleStruct;

/**
 * 
 */
public final class XMLSerializer
{

	/**
	 * reads data from the given stream and creates SimpleStruct, can be called
	 * recurrently
	 * 
	 * @param is
	 * @return SimpleStruct
	 */
	public static SimpleStruct deserialize(InputStream is)
	{
		Document document = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		SimpleStruct result = null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new SalomonErrorHandler());
			document = builder.parse(is);
			// because node no. 0 is <!doctype....>
			Node root = document.getChildNodes().item(0);
			result = StructDeserializer.deserialize(root);
		} catch (Exception e) {
			LOGGER.error("", e);
		}

		return result;
	}

	/**
	 * creates XML out of the given struct
	 * 
	 * @param value
	 * @param os
	 * 
	 */
	public static void serialize(SimpleStruct value, OutputStream os)
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document = null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			LOGGER.error("", e);
		}
		document.appendChild(StructSerializer.serialize(document, value, null));
		try {
			Result result = new StreamResult(os);
			Source source = new DOMSource(document);
			Transformer writer = TransformerFactory.newInstance().newTransformer();
			writer.transform(source, result);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
	}

	private static final Logger LOGGER = Logger.getLogger(XMLSerializer.class);
}
