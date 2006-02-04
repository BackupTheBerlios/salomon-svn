/*
 *
 */

package salomon.engine.platform.serialization;

import org.apache.log4j.Logger;

/**
 * 
 * TODO: add comment.
 * 
 * @author kuba
 * 
 */
interface INodeNames
{
	public static final String ATTR_NAME = "name";

	public static final String ATTR_SIZE = "size";

	public static final String ATTR_VALUE = "value";

	public static final Logger LOGGER = Logger.getLogger(StructDeserializer.class);

	public static final String NODE_ARRAY = "array";

	public static final String NODE_INT = "int";

	public static final String NODE_STRING = "string";

	public static final String NODE_STRUCT = "struct";

}
