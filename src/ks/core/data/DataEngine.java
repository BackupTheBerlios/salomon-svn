/** Java class "DataEngine.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package ks.core.data;

import ks.core.data.attribute.*;
import ks.core.data.dataset.*;
import ks.core.data.ruleset.*;


/**
 *  
 */
public class DataEngine
{
	
	private AttributeManager _attributeManager;

	
	private  DataSetManager _dataSetManager;

	
	private  RuleSetManager _ruleSetManager;

	public DataEngine() {
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
	} // end getTestingDataSetManager

	/**
	 * Does ...
	 * 
	 * @return
	 */
	public RuleSetManager getRuleSetManager()
	{
		// your code here
		return null;
	} // end getRuleSetManager

	/**
	 * Does ...
	 * 
	 * @return
	 */
	public AttributeManager getAttributeManager()
	{
		// your code here
		return null;
	} // end getAttributeManager
} // end DataEngine