

package salomon.engine.platform.data;

import java.sql.SQLException;

import salomon.engine.platform.data.attribute.AttributeManager;
import salomon.engine.platform.data.dataset.DataSetManager;
import salomon.engine.platform.data.ruleset.RuleSetManager;

/**
 *  Class holds  DataSetManager, RuleSetManager and AttributeManager instances.  
 */
public class DataEngine
{
	private AttributeManager _attributeManager;

	private DataSetManager _dataSetManager;

	private RuleSetManager _ruleSetManager;

	public DataEngine()
	{
		_dataSetManager = new DataSetManager();
	}

	public DataSetManager getTrainingDataSetManager()
	{
		return _dataSetManager;
	}

	/**
	 * Does ...
	 * 
	 * @return
	 */
	public DataSetManager getTestingDataSetManager()
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

	/**
	 * TODO: add comment.
	 * 
	 */
	public DataSetManager getDataSetManager()
	{
		// TODO Auto-generated method stub
		return null;
	}
} 
