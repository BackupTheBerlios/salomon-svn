/**
 * 
 */

package salomon.engine.platform.data.attribute;

import salomon.engine.database.DBManager;

import salomon.platform.IInfo;
import salomon.platform.data.IColumn;
import salomon.platform.data.attribute.IAttributeData;
import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.data.dataset.IData;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public class AttributeSet implements IAttributeSet
{
    private AttributeManager _attributeManager;

    private IColumn[] _columns;

    private AttributeSetInfo _info;

    /**
     * @param descriptions
     */
    protected AttributeSet(AttributeManager attributeManager,
            IAttributeDescription[] descriptions, DBManager manager)
    {
        _attributeManager = attributeManager;
        _info = new AttributeSetInfo(_attributeManager, manager);
        _info.setDescriptions(descriptions);
        // initializing columns
        initColumns(descriptions);
    }

    /**
     * @see salomon.platform.data.attribute.IAttributeSet#getDesciptions()
     */
    public AttributeDescription[] getDesciptions() throws PlatformException
    {
        return _info.getDescriptions();
    }

    /**
     * Returns the info.
     * @return The info
     */
    public final IInfo getInfo()
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
        // selecting all data from the dataSet
        IData data = dataSet.selectData(_columns, null);
        return new AttributeData(_info.getDescriptions(), data);
    }

    /**
     * @see salomon.platform.data.attribute.IAttributeSet#setName(java.lang.String)
     */
    public void setName(String name)
    {
        _info.setName(name);
    }

    void initColumns(IAttributeDescription[] descriptions)
    {
        if (descriptions != null) {
            _columns = new IColumn[descriptions.length];
            for (int i = 0; i < descriptions.length; ++i) {
                _columns[i] = descriptions[i].getColumn();
            }
        }
    }
}
