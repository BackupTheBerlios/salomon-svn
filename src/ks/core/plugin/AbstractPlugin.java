/** Java interface "AbstractPlugin.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package ks.core.plugin;

/**
 *  
 */
public abstract class AbstractPlugin implements IDataPlugin, IGraphicalPlugin, Cloneable
{
	///////////////////////////////////////
	// associations
	///////////////////////////////////////
	// operations
	/**
	 * Does ...
	 *  
	 */
	public abstract void initizalize();

	/**
	 * Does ...
	 *  
	 */
	public abstract void destroy();

	/**
	 * Does ...
	 * 
	 * @return
	 */
	public abstract Description getDescription();
	
    public Object clone(){
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    } 
	
} // end Plugin
