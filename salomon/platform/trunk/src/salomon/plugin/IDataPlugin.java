/*
 * Created on 2004-05-22
 *
 */

package salomon.plugin;

import salomon.platform.data.DataEngine;
import salomon.platform.data.Environment;

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