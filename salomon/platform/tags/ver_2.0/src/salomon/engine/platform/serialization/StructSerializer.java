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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import salomon.platform.serialization.IObject;
import salomon.util.serialization.SimpleArray;
import salomon.util.serialization.SimpleInteger;
import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

/**
 * 
 * TODO: add comment.
 * 
 * @author kuba
 * 
 */
final class StructSerializer implements INodeNames
{

    /**
     * Creates xml representation of the node
     * 
     * @param document
     * @param struct
     * @param structName
     * @return created node
     */
    public static Element serialize(Document document, SimpleStruct struct)
    {
        Element result = document.createElement(NODE_STRUCT);
        String[] names = struct.getFieldNames();
        for (String name : names) {
            IObject field = struct.getField(name);
            if (field != null) {
                Element childNode = serializeSimpleType(document, field);
                childNode.setAttribute(ATTR_NAME, name);
                result.appendChild(childNode);
            }
        }

        return result;
    }

    /**
     * TODO: add comment.
     * @param document
     * @param name
     * @param field
     */
    private static Element serializeSimpleType(Document document, IObject field)
    {
        Element childNode = null;
        if (field.getType() == IObject.Types.INT) {
            childNode = getIntNode((SimpleInteger) field, document);
        } else if (field.getType() == IObject.Types.STRING) {
            childNode = getStringNode((SimpleString) field, document);
        } else if (field.getType() == IObject.Types.STRUCT) {
            childNode = serialize(document, (SimpleStruct) field);
        } else if (field.getType() == IObject.Types.ARRAY) {
            childNode = getArrayNode((SimpleArray) field, document);
        }

        return childNode;
    }

    /**
     * creates int node
     * 
     * @param object
     * @param document
     * @param name
     * @return xml node
     */
    private static Element getIntNode(SimpleInteger object, Document document)
    {
        Element result = document.createElement(NODE_INT);
        result.setAttribute(ATTR_VALUE, String.valueOf(object.getValue()));
        return result;
    }

    /**
     * creates string node
     * 
     * @param object
     * @param document
     * @param name
     * @return xml node
     */
    private static Element getStringNode(SimpleString object, Document document)
    {
        Element result = document.createElement(NODE_STRING);
        result.setAttribute(ATTR_VALUE, object.getValue());

        return result;
    }

    /**
     * Creates array node.
     * 
     * @param arrayObject
     * @param document
     * @return xml node
     */
    private static Element getArrayNode(SimpleArray arrayObject,
            Document document)
    {
        Element result = document.createElement(NODE_ARRAY);
        for (IObject item : arrayObject.getValue()) {
            Node childNode = serializeSimpleType(document, item);
            result.appendChild(childNode);
        }

        return result;
    }

}
