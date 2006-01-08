package salomon.engine.platform.data.attribute;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import salomon.platform.data.attribute.IAttribute;
import salomon.platform.data.attribute.description.IIntegerAttributeDescription;
import salomon.platform.exception.PlatformException;

public class IntegerAttributeDescription extends AttributeDescription implements
		IIntegerAttributeDescription {

	protected IntegerAttributeDescription(String name) {
		super(name);
	}

	public IAttribute createAttribute(Object value) throws PlatformException {
		if (value instanceof Integer || value instanceof BigDecimal)
			return new Attribute(this, value);
		else
			try {
				Integer.parseInt(value.toString());
				return new Attribute(this, value);
			} catch (NumberFormatException e) {
				LOGGER.error("", e);
				throw new PlatformException("Neither instance of Integer/BigDecimal "
						+ "nor a String representation of integer in object "
						+ value);
			}
	}

	private static final Logger LOGGER = Logger
			.getLogger(IntegerAttributeDescription.class);
}
