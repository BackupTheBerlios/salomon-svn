/*
 * Created on 2004-05-22
 *
 */

package pl.edu.agh.icsr.salomon.plugin.simpleconsole;

import salomon.plugin.IResult;

/**
 * @author nico
 *  
 */
public class SCResult implements IResult
{
	public static final int SC_OK = 0;
	
	public static final int SC_UNSUPPORTED_QUERY = 1;

	public static final int SC_QUERY_ERROR = 2;	
	
	public String[] columnNames = null;

	public Object[][] data = null;
	
	public String errMessage = null;

	public int resultType = -1;
}