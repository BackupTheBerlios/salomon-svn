/** Java class "IDataPlugin.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package ks.core.plugin;

import ks.core.data.DataEngine;
import ks.core.data.Environment;

/**
 *  
 */
public interface IDataPlugin
{
	///////////////////////////////////////
	// operations
	/**
	 * Does ...
	 * 
	 * @param settings
	 */
	public void doJob(DataEngine engine, Environment environment,
			Settings settings);
	
} // end IDataPlugin
