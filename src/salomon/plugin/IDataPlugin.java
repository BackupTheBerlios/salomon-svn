/*
 * Created on 2004-05-22
 *
 */

package salomon.plugin;

import salomon.core.data.DataEngine;
import salomon.core.data.Environment;

/**
 * @author nico
 *  
 */
public interface IDataPlugin
{
	IResult doJob(DataEngine engine, Environment environment, ISettings settings);
}