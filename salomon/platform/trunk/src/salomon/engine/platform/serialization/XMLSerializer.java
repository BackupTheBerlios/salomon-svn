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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import salomon.platform.serialization.IObject;

/**
 * 
 */
public final class XMLSerializer
{
private static final Logger LOGGER = Logger.getLogger(XMLSerializer.class);

	public IObject deserialize(InputStream is)
	{
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        IObject result = null;
        try {
            DocumentBuilder builder =  factory.newDocumentBuilder();
            document = builder.parse(is);
            Node root = document.getChildNodes().item(0);
            result = StructSerializer.deserialize(root);
        } catch (Exception e) {
        	LOGGER.error("", e);
        }
        
        return result;
	}

    
    
	public void serialize(IObject value, InputStream is)
	{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
			DocumentBuilder builder =  factory.newDocumentBuilder();
            document=  builder.newDocument();
		} catch (ParserConfigurationException e) {
            LOGGER.error("", e);
		}
        //document.createE
        Element root = document.createElement(StructSerializer.NODE_STRUCT); 
        document.appendChild(root);
        System.out.println(document.toString());
	}
}
