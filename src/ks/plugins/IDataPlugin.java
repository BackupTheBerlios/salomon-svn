/** Java class "IDataPlugin.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package ks.plugins;

import ks.data.DataEngine;
import ks.data.Environment;

/**
 *  
 */
public class IDataPlugin
{
	///////////////////////////////////////
	// associations
	/**
	 *  
	 */
	public Environment environment;

	/**
	 *  
	 */
	public ISettings iSettings;

	/**
	 *  
	 */
	public DataEngine dataEngine;

	///////////////////////////////////////
	// operations
	/**
	 * Does ...
	 * 
	 * @param settings
	 */
	public void doJob(DataEngine engine, Environment environment,
			ISettings settings)
	{
		// your code here
	} // end doJob
} // end IDataPlugin
