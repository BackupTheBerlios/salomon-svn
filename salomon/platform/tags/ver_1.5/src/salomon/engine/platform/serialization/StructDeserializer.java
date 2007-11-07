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

import java.util.LinkedList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
final class StructDeserializer implements INodeNames
{

    /**
     * creates and IObjest out of the given node
     * 
     * @param node
     * @return IObject
     */
    public static SimpleStruct deserialize(Node node)
    {
        SimpleStruct result = new SimpleStruct();
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            if (Node.ELEMENT_NODE == childNode.getNodeType()) {
                String nodeName = getNodeName(childNode);
                IObject fieldObject = deserializeObject(childNode);
                result.setField(nodeName, fieldObject);
            }
        }
        return result;
    }

    /**
     * TODO: add comment.
     * @param childNode
     * @return
     */
    private static IObject deserializeObject(Node childNode)
    {
        String nodeType = childNode.getNodeName();
        IObject fieldObject = null;
        if (NODE_INT.equals(nodeType)) {
            SimpleInteger integerObject = new SimpleInteger();
            integerObject.setValue(getIntValue(childNode));
            fieldObject = integerObject;
        } else if (NODE_STRING.equals(nodeType)) {
            SimpleString stringObject = new SimpleString();
            stringObject.setValue(getStringValue(childNode));
            fieldObject = stringObject;
        } else if (NODE_STRUCT.equals(nodeType)) {
            fieldObject = StructDeserializer.deserialize(childNode);
        } else if (NODE_ARRAY.equals(nodeType)) {
            fieldObject = StructDeserializer.deserializeSimpleArray(childNode);
        }

        return fieldObject;
    }

    private static SimpleArray deserializeSimpleArray(Node arrayNode)
    {
        LinkedList<IObject> items = new LinkedList<IObject>();
        NodeList childNodeList = arrayNode.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); ++i) {
            Node childNode = childNodeList.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                IObject item = deserializeObject(childNode);
                items.add(item);
            }
        }

        return new SimpleArray(items.toArray(new IObject[items.size()]));
    }

    private static int getIntValue(Node node)
    {
        String value = node.getAttributes().getNamedItem(ATTR_VALUE).getNodeValue();
        int result = 0;
        try {
            result = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            LOGGER.error("couldn't parse " + value, e);
        }
        return result;
    }

    private static String getNodeName(Node node)
    {
        String result = node.getAttributes().getNamedItem(ATTR_NAME).getNodeValue();
        return result;
    }

    private static String getStringValue(Node node)
    {
        String result = node.getAttributes().getNamedItem(ATTR_VALUE).getNodeValue();
        return result;
    }

}
