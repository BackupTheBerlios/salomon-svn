/*
 * Created on 2004-05-22
 *
 */

package salomon.plugin;

import java.awt.Component;

/**
 * @author nico
 *  
 */
public interface IResultComponent
{
	Component getComponent(IResult result);

	IResult getDefaultResult();
}