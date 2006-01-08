/**
 * 
 */
package salomon.engine.platform.data.attribute;

import java.sql.SQLException;
import java.util.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.platform.data.attribute.*;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

/**
 * @author Lukasz
 * 
 */
public class AttributeSet implements IAttributeSet {

	private LinkedList<IAttribute[]> _attributesList;

	private boolean _restrictiveTypeCheck;

	IAttributeDescription[] _descriptions;

	private String _name = "";

	private String _info = "";
	
	private int _attributeSetId=-1;;

	protected AttributeSet(IAttributeDescription[] descriptions,
			boolean restrictiveTypeCheck) {
		_attributesList = new LinkedList<IAttribute[]>();
		_descriptions = descriptions;
		_restrictiveTypeCheck = restrictiveTypeCheck;
	}

	public int getAttributeSetId(){
		return _attributeSetId;
	}
	
	protected void setAttributeSetId(int id){
		_attributeSetId=id;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.data.attribute.IAttributeSet#close()
	 */
	public void close() throws PlatformException {
		LOGGER.debug("Closing AttributeSet (cleaning queue)");
		_attributesList = null;
		_attributesList = new LinkedList<IAttribute[]>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.data.attribute.IAttributeSet#getAttributes()
	 */
	public IAttribute[] getAttributes() throws PlatformException {
		return _attributesList.getFirst();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.data.attribute.IAttributeSet#getAttribute(salomon.platform.data.attribute.description.IAttributeDescription)
	 */
	public IAttribute getAttribute(IAttributeDescription attributeDescription)
			throws PlatformException {
		for (IAttribute attr : _attributesList.getFirst()) {
			if (attr.getDescription().equals(attributeDescription))
				return attr;
		}
		LOGGER.warn("Attribute of type " + attributeDescription
				+ " was not found!");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.data.attribute.IAttributeSet#getDesciptions()
	 */
	public IAttributeDescription[] getDesciptions() throws PlatformException {
		return _descriptions;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.data.attribute.IAttributeSet#next()
	 */
	public boolean next() throws PlatformException {
		return (_attributesList.removeFirst() == null) ? false : true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.data.attribute.IAttributeSet#add(salomon.platform.data.attribute.IAttribute[])
	 */
	public void add(IAttribute[] attributes) throws PlatformException {
		if (this._restrictiveTypeCheck) {
			if (attributes.length != _descriptions.length)
				throw new PlatformException(
						"Restrictive type check failed! Incorrect number od attributes given (is "
								+ attributes.length + " should be "
								+ _descriptions.length + ").");
			for (int i = 0; i < attributes.length; i++) {
				if (!attributes[i].getDescription().equals(_descriptions[i])) {
					throw new PlatformException(
							"Restrictive type check failed! Incorrect type of attr no. "
									+ i + " (is "
									+ attributes[i].getDescription()
									+ " should be " + _descriptions[i] + ").");
				}
			}
		}
		_attributesList.add(attributes);
		LOGGER.debug("Adding to attributeSet " + this.toString()
				+ " attributes " + attributes);
	}

	@Override
	public String toString() {
		return "Attribute set name:" + _name;
	}

	private static final Logger LOGGER = Logger.getLogger(AttributeSet.class);

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
		if(_name.length()>10){
			_name=_name.substring(0,10);
			LOGGER.warn("Name too long (should be max 10 chars). Trimming... ("+_name+")");
		}
	}

	public String getInfo() {
		return _info;
	}

	public void setInfo(String info) {
		this._info = info;
		if(_info.length()>250){
			_name=_name.substring(0,250);
			LOGGER.warn("Info too long (should be max 250 chars). Trimming... ("+_info+")");
		}
	}

}
