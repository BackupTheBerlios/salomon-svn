package salomon.engine.platform.data.attribute;

import java.util.HashSet;
import java.util.Set;

import salomon.platform.data.attribute.IAttribute;
import salomon.platform.data.attribute.description.IEnumAttributeDescription;
import salomon.platform.exception.PlatformException;

public class EnumAttributeDescription extends AttributeDescription implements
		IEnumAttributeDescription {

	private Set _possibleValuesSet = new HashSet();

	protected EnumAttributeDescription(String name, Set possibleValuesSet) {
		super(name);
		_possibleValuesSet = possibleValuesSet;
	}

	@SuppressWarnings("unchecked")
	protected EnumAttributeDescription(String name, Object[] possibleValuesSet) {
		super(name);
		_possibleValuesSet = new HashSet();
		if (possibleValuesSet != null)
			for (Object ob : possibleValuesSet)
				_possibleValuesSet.add(ob);
	}

	public Object[] getPossibleValues() {
		return _possibleValuesSet.toArray();
	}

	@SuppressWarnings("unchecked")
	public void addPossibleValue(Object obj) {
		_possibleValuesSet.add(obj);
	}

	@Override
	public IAttribute createAttribute(Object value) throws PlatformException {
		if (this._possibleValuesSet.contains(value.toString()))
			return new Attribute(this, value);
		else
			throw new PlatformException(
					"Trying to insert not allowed enum value (" + value + ")");
	}

}
