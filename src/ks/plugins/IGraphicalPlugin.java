/** Java class "IGraphicalPlugin.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package ks.plugins;

import javax.swing.JPanel;

/**
 *  
 */
public interface IGraphicalPlugin
{
	///////////////////////////////////////
	// operations
	/**
	 * Returns panel, which enables modification of settings
	 *  
	 */
	public JPanel getSettingsPanel();
	
	/**
	 * Returns panel which shows result of plugins execution
	 * 
	 * @return
	 */
	public JPanel getResultPanel();
	
	public Settings getSettings();
	
} // end IGraphicalPlugin
