/*
 * Created on 2004-05-22
 *
 */

package pl.edu.agh.icsr.salomon.plugin.simpleconsole;

import java.io.Serializable;

import salomon.plugin.IResult;

/**
 * @author nico
 *  
 */
public final class SCResult implements IResult, Serializable
{
	public static final int SC_OK = 0;

	public static final int SC_UNSUPPORTED_QUERY = 1;

	public static final int SC_QUERY_ERROR = 2;

	private String[] _columnNames = null;

	private Object[][] _data = null;

	private String _errMessage = null;

	private boolean _isSuccessful = false;

	private int _resultType = -1;

	public void parseResult(String result)
	{
	}

	public String resultToString()
	{
		return (_errMessage == null) ? "Some rows selected" : _errMessage;
	}

	public boolean isSuccessful()
	{
		return _isSuccessful;
	}

	public void setSuccessful(boolean isSuccessful)
	{
		_isSuccessful = isSuccessful();
	}

	/**
	 * @return Returns the columnNames.
	 */
	public String[] getColumnNames()
	{
		return _columnNames;
	}

	/**
	 * @return Returns the data.
	 */
	public Object[][] getData()
	{
		return _data;
	}

	/**
	 * @return Returns the errorMessage.
	 */
	public String getErrorMessage()
	{
		return _errMessage;
	}

	/**
	 * 
	 * @param errMsg
	 *            errMsg to set
	 */
	public void setErrorMessage(String errMsg)
	{
		_errMessage = errMsg;
	}

	/**
	 * 
	 * @param columnNames
	 * @param data
	 */
	public void setData(String[] columnNames, Object[][] data)
	{
		_columnNames = columnNames;
		_data = data;
	}

	/**
	 * @return Returns the resultType.
	 */
	public int getResultType()
	{
		return _resultType;
	}

	/**
	 * @param resultType
	 *            The resultType to set.
	 */
	public void setResultType(int resultType)
	{
		this._resultType = resultType;
	}
}