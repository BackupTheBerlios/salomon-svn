
package salomon.controller;

import salomon.platform.IManagerEngine;

/**
 * An interface implemented by all controllers. 
 */
public interface IController
{

	public void start(IManagerEngine managerEngine);
    
    public void exit();
} // end IManager

