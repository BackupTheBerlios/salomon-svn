/*
 * Created on 2004-05-22
 *
 */

package salomon.plugin;

import salomon.core.data.DataEngine;
import salomon.core.data.Environment;

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