/*
 * Copyright (C) 2004 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package pl.edu.agh.iisg.salomon.plugin.datasetunion;

import java.util.StringTokenizer;

import salomon.plugin.ISettings;

/**
 *  
 */
public final class USettings implements ISettings
{
	private String _firstDataSet;

	private String _resultDataSet;

	private String _secondDataSet;

	/**
	 * @return Returns the firstDataSet.
	 */
	public String getFirstDataSet()
	{
		return _firstDataSet;
	}

	/**
	 * @return Returns the resultDataSet.
	 */
	public String getResultDataSet()
	{
		return _resultDataSet;
	}

	/**
	 * @return Returns the secondDataSet.
	 */
	public String getSecondDataSet()
	{
		return _secondDataSet;
	}

	/**
	 * @see salomon.plugin.ISettings#parseSettings(java.lang.String)
	 */
	public void parseSettings(String stringSettings)
	{
		if (stringSettings.trim().equals("")) {
			_firstDataSet = _secondDataSet = _resultDataSet = null;
		} else {
			String separator = ";";
			StringTokenizer tokenizer = new StringTokenizer(stringSettings,
					separator, true);
			_firstDataSet = tokenizer.nextToken();
			if (_firstDataSet.equals(separator)) {
				_firstDataSet = null;
			} else {
				// missing separator
				tokenizer.nextToken();
			}
			_secondDataSet = tokenizer.nextToken();
			if (_secondDataSet.equals(separator)) {
				_secondDataSet = null;
			} else {
				// missing separator
				tokenizer.nextToken();
			}
			// last token treated in the special way
			if (tokenizer.hasMoreTokens()) {
				_resultDataSet = tokenizer.nextToken();
			} else {
				_resultDataSet = null;
			}
		}
	}

	/**
	 * @param firstDataSet The firstDataSet to set.
	 */
	public void setFirstDataSet(String firstDataSet)
	{
		_firstDataSet = firstDataSet;
	}

	/**
	 * @param resultDataSet The resultDataSet to set.
	 */
	public void setResultDataSet(String resultDataSet)
	{
		_resultDataSet = resultDataSet;
	}

	/**
	 * @param secondDataSet The secondDataSet to set.
	 */
	public void setSecondDataSet(String secondDataSet)
	{
		_secondDataSet = secondDataSet;
	}

	/**
	 * @see salomon.plugin.ISettings#settingsToString()
	 */
	public String settingsToString()
	{

		String settings = ((_firstDataSet == null) ? "" : _firstDataSet) + ";";
		settings += ((_secondDataSet == null) ? "" : _secondDataSet) + ";";
		settings += ((_resultDataSet == null) ? "" : _resultDataSet);
        
		return settings;
	}
}