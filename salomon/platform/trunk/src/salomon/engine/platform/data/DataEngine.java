

package salomon.engine.platform.data;

import java.sql.SQLException;

import salomon.engine.platform.data.attribute.AttributeManager;
import salomon.engine.platform.data.dataset.DataSetManager;
import salomon.engine.platform.data.rule.RuleSetManager;
import salomon.platform.data.IDataEngine;

/**
 *  Class holds  DataSetManager, RuleSetManager and AttributeManager instances.  
 */
public class DataEngine implements IDataEngine
{
	private AttributeManager _attributeManager;

	private DataSetManager _dataSetManager;

	private RuleSetManager _ruleSetManager;

	public DataEngine()
	{
		_dataSetManager = new DataSetManager();
	}

	/**
	 * Does ...
	 * 
	 * @return
	 */
	public DataSetManager getDataSetManager()
	{
		return _dataSetManager;
	} 

	/**
	 * Does ...
	 * 
	 * @return
	 */
	public RuleSetManager getRuleSetManager()
	{
		
		return null;
	} 

	/**
	 * Does ...
	 * 
	 * @return
	 */
	public AttributeManager getAttributeManager()
	{
		
		return null;
	} 
    
	public DBManager getDbManager() throws SQLException, ClassNotFoundException
	{
		return DBManager.getInstance();
	}	
} 
