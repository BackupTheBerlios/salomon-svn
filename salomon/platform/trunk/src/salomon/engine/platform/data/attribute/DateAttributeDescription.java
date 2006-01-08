package salomon.engine.platform.data.attribute;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import salomon.platform.data.attribute.IAttribute;
import salomon.platform.data.attribute.description.IDateAttributeDescription;
import salomon.platform.exception.PlatformException;

public class DateAttributeDescription extends AttributeDescription implements
		IDateAttributeDescription {

	public DateAttributeDescription(String name) {
		super(name);
	}

	@SuppressWarnings("deprecation")
	public IAttribute createAttribute(Object value) throws PlatformException {
		if (value instanceof java.sql.Date || value instanceof java.util.Date)
			return new Attribute(this, value);
		else
			try {
				java.util.Date.parse(value.toString());// TODO maybe sth
														// cooler...
				return new Attribute(this, value);
			} catch (NumberFormatException e) {
				LOGGER.error("", e);
				throw new PlatformException("Neither instance of Date "
						+ "nor a String representation of date in object "
						+ value);
			}
	}

	private static final Logger LOGGER = Logger
			.getLogger(IntegerAttributeDescription.class);
}
