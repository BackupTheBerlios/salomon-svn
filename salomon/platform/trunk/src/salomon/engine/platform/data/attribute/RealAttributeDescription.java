package salomon.engine.platform.data.attribute;

import org.apache.log4j.Logger;

import salomon.platform.data.attribute.IAttribute;
import salomon.platform.data.attribute.description.IRealAttributeDescription;
import salomon.platform.exception.PlatformException;

public class RealAttributeDescription extends AttributeDescription implements
		IRealAttributeDescription {

	protected RealAttributeDescription(String name) {
		super(name);
	}

	public IAttribute createAttribute(Object value) throws PlatformException {
		if (value instanceof Double || value instanceof Float)
			return new Attribute(this, value);
		else
			try {
				Double.parseDouble(value.toString());
				return new Attribute(this, value);
			} catch (NumberFormatException e) {
				LOGGER.error("", e);
				throw new PlatformException("Neither instance of Double/Float "
						+ "nor a String representation of double in object "
						+ value);
			}
	}

	private static final Logger LOGGER = Logger
			.getLogger(RealAttributeDescription.class);
}
