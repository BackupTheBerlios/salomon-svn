
package pl.edu.agh.iisg.salomon.plugin.datasetunion;

import salomon.plugin.IResult;

/**
 * 
 * TODO: add comment.
 * @author krzychu
 * 
 */
public class UResult implements IResult
{

    private boolean _isSuccessfull = false;
	/* (non-Javadoc)
	 * @see salomon.plugin.IResult#parseResult(java.lang.String)
	 */
	public void parseResult(String result)
	{
		
	}

	/* (non-Javadoc)
	 * @see salomon.plugin.IResult#resultToString()
	 */
	public String resultToString()
	{		
		return null;
	}

	/* (non-Javadoc)
	 * @see salomon.plugin.IResult#isSuccessful()
	 */
	public boolean isSuccessful()
	{		
		return _isSuccessfull;
	}
    

	/**
	 * @param isSuccessfull The isSuccessfull to set.
	 */
	public void setSuccessfull(boolean isSuccessfull)
	{
		_isSuccessfull = isSuccessfull;
	}
}
