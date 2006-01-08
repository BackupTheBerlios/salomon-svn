package salomon.engine.platform.data.attribute;

import salomon.platform.data.attribute.IAttribute;
import salomon.platform.data.attribute.description.IStringAttributeDescription;
import salomon.platform.exception.PlatformException;

public class StringAttributeDescription extends AttributeDescription implements
		IStringAttributeDescription {

	protected StringAttributeDescription(String name) {
		super(name);
	}

	public IAttribute createAttribute(Object value) throws PlatformException {
		return new Attribute(this, value);
	}
}
