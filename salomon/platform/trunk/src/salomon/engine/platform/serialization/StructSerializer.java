/*
 *
 * Author: Jakub Pawlowski
 * Created on:2004-12-18 01:45:36
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
class StructSerializer implements INodeNames
{

	/**
	 * creates xml representation of the node
	 * 
	 * @param document
	 * @param struct
	 * @param structName
	 * @return created node
	 */
	public static Element serialize(Document document, SimpleStruct struct,
			String structName)
	{
		Element result = document.createElement(NODE_STRUCT);
		if (structName != null) {
			result.setAttribute(ATTR_NAME, structName);
		}
		String[] names = struct.getFieldNames();
		for (String name : names) {
			IObject object = struct.getField(name);

			if (object.getType() == IObject.Types.INT) {
				result.appendChild(getIntNode((SimpleInteger) object, document,
						name));
			} else if (object.getType() == IObject.Types.STRING) {
				result.appendChild(getStringNode((SimpleString) object,
						document, name));
			} else if (object.getType() == IObject.Types.STRUCT) {
				result.appendChild(serialize(document, (SimpleStruct) object,
						name));
			}

		}

		return result;
	}

	/**
	 * creates int node
	 * 
	 * @param object
	 * @param document
	 * @param name
	 * @return xml node
	 */
	private static Node getIntNode(SimpleInteger object, Document document,
			String name)
	{
		Element result = document.createElement(NODE_INT);
		result.setAttribute(ATTR_NAME, name);
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
	private static Node getStringNode(SimpleString object, Document document,
			String name)
	{
		Element result = document.createElement(NODE_STRING);
		result.setAttribute(ATTR_NAME, name);
		result.setAttribute(ATTR_VALUE, object.getValue());

		return result;
	}
}
