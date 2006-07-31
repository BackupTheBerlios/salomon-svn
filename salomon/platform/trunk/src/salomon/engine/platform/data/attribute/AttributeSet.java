/**
 * 
 */

package salomon.engine.platform.data.attribute;

import salomon.engine.database.DBManager;
import salomon.engine.database.ExternalDBManager;
import salomon.platform.data.attribute.IAttributeData;
import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.exception.PlatformException;

/**
 * @author Lukasz
 * 
 */
public class AttributeSet implements IAttributeSet
{
    private AttributeManager _attributeManager;

    private AttributeSetInfo _info;

    /**
     * @param descriptions
     */
    protected AttributeSet(AttributeManager attributeManager,
            IAttributeDescription[] descriptions, DBManager manager,
            ExternalDBManager externalDBManager)
    {
        _attributeManager = attributeManager;
        _info = new AttributeSetInfo(_attributeManager, manager,
                externalDBManager);
        _info.setDescriptions(descriptions);
    }

    /**
     * @see salomon.platform.data.attribute.IAttributeSet#getDesciptions()
     */
    public IAttributeDescription[] getDesciptions() throws PlatformException
    {
        return _info.getDescriptions();
    }

    /**
     * Returns the info.
     * @return The info
     */
    public final AttributeSetInfo getInfo()
    {
        return _info;
    }

    /**
     * @see salomon.platform.data.attribute.IAttributeSet#getName()
     */
    public String getName()
    {
        return (_info == null ? null : _info.getName());
    }

    /**
     * @see salomon.platform.data.attribute.IAttributeSet#selectAttributeData(salomon.platform.data.dataset.IDataSet)
     */
    public IAttributeData selectAttributeData(IDataSet dataSet)
            throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method AttributeSet.selectAttributeData() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.attribute.IAttributeSet#setName(java.lang.String)
     */
    public void setName(String name)
    {
        _info.setName(name);
    }

}
