/*
 * Created on 2004-05-22
 *
 */

package pl.edu.agh.icsr.salomon.plugin.averageprice;

import java.io.Serializable;

import salomon.plugin.IResult;

/**
 * @author nico
 *  
 */
public final class APResult implements IResult, Serializable
{
	private double _averagePrice = 0.0;

	private boolean _isSuccessful = false;

	public void parseResult(String result)
	{
		_averagePrice = Double.parseDouble(result);
	}

	public String resultToString()
	{
		return Double.toString(_averagePrice);
	}

	public void setSuccessful(boolean isSuccessful)
	{
		_isSuccessful = isSuccessful;
	}

	public boolean isSuccessful()
	{
		return _isSuccessful;
	}

	/**
	 * @return Returns the averagePrice.
	 */
	public double getAveragePrice()
	{
		return _averagePrice;
	}

	/**
	 * @param averagePrice
	 *            The averagePrice to set.
	 */
	public void setAveragePrice(double averagePrice)
	{
		_averagePrice = averagePrice;
	}
}