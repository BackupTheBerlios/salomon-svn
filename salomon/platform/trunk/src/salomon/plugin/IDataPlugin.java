/*
 * Created on 2004-05-22
 *
 */

package salomon.plugin;

import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;

/** 
 * Main interface implemented by all plugins.   
 */
public interface IDataPlugin
{
    /**
     * Does plugin job. 
     */
	IResult doJob(IDataEngine engine, IEnvironment environment, ISettings settings);
}