/*
 * Created on 2004-05-22
 *
 */

package salomon.plugin;

import salomon.engine.platform.DataEngine;
import salomon.engine.platform.Environment;

/** 
 * Main interface implemented by all plugins.   
 */
public interface IDataPlugin
{
    /**
     * Does plugin job. 
     */
	IResult doJob(DataEngine engine, Environment environment, ISettings settings);
}