/*
 *
 * Author: Jakub Pawlowski
 * Created on:2004-12-17 19:46:05
 */

package salomon.engine.platform.serialization;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import salomon.platform.serialization.IObject;
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
public class StructSerializer
{

	/**
	 * creates and IObjest out of the given node
	 * 
	 * @param node
	 * @return IObject
	 */
	public static IObject deserialize(Node node)
	{
		SimpleStruct result = new SimpleStruct();
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node tmp = nodeList.item(i);
			String nodeType = tmp.getNodeName();
			if (NODE_INT.equals(nodeType)) {
				String nodeName = getNodeName(tmp);
				SimpleInteger object = new SimpleInteger();
				object.setValue(getIntValue(tmp));
				result.setField(nodeName, object);
			} else if (NODE_STRING.equals(nodeType)) {
				String nodeName = getNodeName(tmp);
				SimpleString object = new SimpleString();
				object.setValue(getStringValue(tmp));
				result.setField(nodeName, object);
			} else if (NODE_STRUCT.equals(nodeType)) {
				String nodeName = getNodeName(tmp);
				result.setField(nodeName, StructSerializer.deserialize(tmp));
			}
		}
		return result;
	}
    

    private static int getIntValue(Node node)
	{
		String value = node.getAttributes().getNamedItem(ATTR_VALUE)
				.getNodeValue();
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
		String result = node.getAttributes().getNamedItem(ATTR_NAME)
				.getNodeValue();
		return result;
	}

	private static String getStringValue(Node node)
	{
		String result = node.getAttributes().getNamedItem(ATTR_VALUE)
				.getNodeValue();
		return result;
	}

	private static final String ATTR_NAME = "name";

	private static final String ATTR_VALUE = "value";

	private static final Logger LOGGER = Logger
			.getLogger(StructSerializer.class);

	private static final String NODE_INT = "int";

	private static final String NODE_STRING = "string";

	/**
	 * Comment for <code>NODE_STRUCT</code>
	 */
	public static final String NODE_STRUCT = "struct";

}
