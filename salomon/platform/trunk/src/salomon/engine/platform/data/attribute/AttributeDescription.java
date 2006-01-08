package salomon.engine.platform.data.attribute;

import org.apache.log4j.Logger;

import salomon.engine.platform.data.attribute.*;
import salomon.platform.data.attribute.*;
import salomon.platform.data.attribute.description.*;
import salomon.platform.exception.PlatformException;

public abstract class AttributeDescription implements
		IIntegerAttributeDescription {

	private String _name;

	protected AttributeDescription(String name) {
		_name = name;
	}

	public String getName() {
		return _name;
	}

	public IAttribute createAttribute(Object value) throws PlatformException {
		return new Attribute(this, value);
	}

	public boolean equals(AttributeDescription a) {
		return (_name.equals(a.getName()));
	}

	private static final Logger LOGGER = Logger
			.getLogger(AttributeDescription.class);
}
