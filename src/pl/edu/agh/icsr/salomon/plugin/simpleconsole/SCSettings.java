/*
 * Created on 2004-05-22
 *
 */

package pl.edu.agh.icsr.salomon.plugin.simpleconsole;

import salomon.plugin.ISettings;

/**
 * @author nico
 *  
 */
public class SCSettings implements ISettings
{
	private String _query = null;

	public void parseSettings(String stringSettings)
	{
		_query = stringSettings;
	}

	public String settingsToString()
	{
		return (_query == null) ? "" : _query;
	}

	/**
	 * @return Returns the query.
	 */
	public String getQuery()
	{
		return (_query == null) ? "" : _query;
	}

	/**
	 * @param query
	 *            The query to set.
	 */
	public void setQuery(String query)
	{
		_query = query;
	}
}