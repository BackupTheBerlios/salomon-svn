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

import org.apache.log4j.Logger;

import salomon.plugin.IResult;

/**
 * 
 */
public class UResult implements IResult
{

	private boolean _isSuccessfull = false;

	private String _result;

	/**
	 * @see salomon.plugin.IResult#isSuccessful()
	 */
	public boolean isSuccessful()
	{
		return _isSuccessfull;
	}

	/**
	 * @see salomon.plugin.IResult#parseResult(java.lang.String)
	 */
	public void parseResult(String result)
	{
		_result = result;
	}

	/**
	 * @see salomon.plugin.IResult#resultToString()
	 */
	public String resultToString()
	{
		return _result;
	}

	/**
	 * @param isSuccessfull The isSuccessfull to set.
	 */
	public void setSuccessfull(boolean isSuccessfull)
	{
		_isSuccessfull = isSuccessfull;
	}

	void setResult(String result)
	{
		LOGGER.warn("Workaround. Don't use this method");
		_result = result;
	}

	private static final Logger LOGGER = Logger.getLogger(UResult.class);
}
