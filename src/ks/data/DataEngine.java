/** Java class "DataEngine.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package ks.data;

import ks.plugins.IDataPlugin;

/**
 *  
 */
public class DataEngine
{
	///////////////////////////////////////
	// associations
	/**
	 *  
	 */
	public IDataPlugin iDataPlugin;

	/**
	 *  
	 */
	public AttributeManager attributeManager;

	/**
	 *  
	 */
	public DataSetManager dataSetManager;

	/**
	 *  
	 */
	public RuleSetManager ruleSetManager;

	///////////////////////////////////////
	// operations
	/**
	 * Does ...
	 * 
	 * @return
	 */
	public DataSetManager getTrainingDataSetManager()
	{
		// your code here
		return null;
	} // end getTrainingDataSetManager

	/**
	 * Does ...
	 * 
	 * @return
	 */
	public DataSetManager getTestingDataSetManager()
	{
		// your code here
		return null;
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
