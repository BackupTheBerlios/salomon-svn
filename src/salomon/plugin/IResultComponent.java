/*
 * Created on 2004-05-22
 *
 */

package salomon.plugin;

import java.awt.Component;

/**
 * Interface represents object, which is responsible for showing result of
 * plugin execution.
 *
 */
public interface IResultComponent
{
    /**
     * Method creates component representing result of plugin execution. 
     * 
     * @param result of plugin execution
     * @return component showing given result
     */
	Component getComponent(IResult result);

    /**
     * Method returns default result of plugin execution. 
     * 
     * @return default result of plugin execution.
     */
	IResult getDefaultResult();
}