/** Java interface "Plugin.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package ks.plugins;

/**
 *  
 */
public interface Plugin
{
	///////////////////////////////////////
	// associations
	///////////////////////////////////////
	// operations
	/**
	 * Does ...
	 *  
	 */
	public void initizalize();

	/**
	 * Does ...
	 *  
	 */
	public void destroy();

	/**
	 * Does ...
	 * 
	 * @return
	 */
	public Description getDescription();
} // end Plugin
