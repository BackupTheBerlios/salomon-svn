
package salomon.controller;

import salomon.core.IManagerEngine;

/**
 * An interface implemented by all controllers. 
 */
public interface IController
{

	public void start(IManagerEngine managerEngine);
    
    public void exit();
} // end IManager

